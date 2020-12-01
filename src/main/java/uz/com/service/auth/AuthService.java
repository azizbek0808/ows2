package uz.com.service.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.GrantType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;
import uz.com.dto.auth.AuthUserDto;
import uz.com.dto.auth.AuthUserRefreshTokenDto;
import uz.com.dto.auth.ChangePasswordDto;
import uz.com.dto.auth.SessionDto;
import uz.com.property.ServerProperties;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.utils.UserSession;
import uz.com.validator.auth.UserServiceValidator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements IAuthService {


    public static String OAUTH_AUTH_URL = "/oauth/token";
    private static String SERVER_URL;
    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final UserSession userSession;
    private final PasswordEncoder userPasswordEncoder;
    private final BaseUtils utils;
//    private final UserDaoImpl userDao;
//    private final IErrorRepository errorRepository;

    private final UserServiceValidator userServiceValidator;
    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;
    @Resource(name = "tokenServices")
    AuthorizationServerTokenServices authorizationServerTokenServices;
    @Value("${oauth2.clientId}")
    private String clientId;
    @Value("${oauth2.clientSecret}")
    private String clientSecret;


    @Autowired
    public AuthService(BaseUtils utils, ServerProperties serverProperties, UserSession userSession, PasswordEncoder userPasswordEncoder, /*UserDaoImpl userDao,*/ /*IErrorRepository errorRepository,*/ UserServiceValidator userServiceValidator) {
        this.utils = utils;

        SERVER_URL = "http://" + serverProperties.getIp() + ":" + serverProperties.getPort() + "";
        this.userSession = userSession;
        this.userPasswordEncoder = userPasswordEncoder;
//        this.userDao = userDao;
        /*this.errorRepository = errorRepository;*/
        this.userServiceValidator = userServiceValidator;
//        SERVER_URL = "https://" + serverProperties.getUrl();
        OAUTH_AUTH_URL = SERVER_URL + OAUTH_AUTH_URL;
    }

    @Override
    public ResponseEntity<DataDto<SessionDto>> login(AuthUserDto user, HttpServletRequest request) {

        userServiceValidator.validateOnAuth(user);
        try {
//            clearExistingTokens(user);
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(OAUTH_AUTH_URL);
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", GrantType.PASSWORD.getValue()));
            nameValuePairs.add(new BasicNameValuePair("username", user.getUserName()));
            nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.addHeader(HttpHeaders.AUTHORIZATION, getAuthorization());
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpclient.execute(httppost);
            return getAuthDtoDataDto(user, response, true, request);

        } catch (Exception ex) {
            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> logout(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            tokenServices.revokeToken(tokenId);
            return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
        }

        return new ResponseEntity<>(new DataDto<>(false), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<SessionDto>> refreshToken(AuthUserRefreshTokenDto refreshToken, HttpServletRequest request) {
        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(OAUTH_AUTH_URL);

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", GrantType.REFRESH_TOKEN.getValue()));
            nameValuePairs.add(new BasicNameValuePair("refresh_token", refreshToken.getRefreshToken()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httppost.addHeader(HttpHeaders.AUTHORIZATION, getAuthorization());
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpclient.execute(httppost);

            return getAuthDtoDataDto(new AuthUserDto(), response, false, request);

        } catch (IOException | RuntimeException e) {
            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(HttpStatus.UNAUTHORIZED.value(),
                    e.getMessage())), HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<DataDto<SessionDto>> getAuthDtoDataDto(AuthUserDto user, HttpResponse response,
                                                                  boolean authentication, HttpServletRequest request) throws IOException {
        JsonNode json_auth = new ObjectMapper().readTree(EntityUtils.toString(response.getEntity()));

        if (!json_auth.has("error")) {
            String token = json_auth.get("access_token").asText();

            SessionDto authDto = SessionDto.builder()
                    .expiresIn(json_auth.get("expires_in").asLong())
                    .sessionToken(token)
                    .refreshToken(json_auth.get("refresh_token").asText())
                    .tokenType(json_auth.get("token_type").asText())
                    .scope(json_auth.get("scope").asText()).build();
            return new ResponseEntity<>(new DataDto<>(authDto), HttpStatus.OK);
        } else {
            String error_description = json_auth.has("error_description") ? json_auth.get("error_description").asText() : null;
            if (error_description == null || error_description.isEmpty()) {
                error_description = "Username or password is wrong custom message";
            } else if (error_description.contains("NoResultException")) {
                error_description = "Username or password is wrong or this user is not active in this System";
            }
            if (error_description.startsWith("user with user name")) {
                return new ResponseEntity<>(new DataDto<>(new AppErrorDto(HttpStatus.NOT_FOUND.value(),
                        error_description)), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(HttpStatus.valueOf(response.getStatusLine().getStatusCode()).value(),
                    error_description)), HttpStatus.valueOf(response.getStatusLine().getStatusCode()));
        }

    }

    private String getAuthorization() {
        return "Basic " + utils.encodeToBase64(clientId + ":" + clientSecret);
    }


    @Override
    public ResponseEntity<DataDto<Boolean>> changePassword(ChangePasswordDto dto) {
        /*User user = userDao.find(UserCriteria.childBuilder().username(userSession.getUserName()).build());
        if (!userPasswordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            *//*throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.PASSWORD_INCORRECT, utils.toErrorParams("")));*//*
        }
        List<FunctionParam> params = new ArrayList<>();
        params.add(new FunctionParam(userSession.getUser().getId(), java.sql.Types.BIGINT));
        params.add(new FunctionParam(userPasswordEncoder.encode(dto.getNewPassword()), java.sql.Types.VARCHAR));
        Boolean isChange = userRepository.call(params, "changePassword", java.sql.Types.BOOLEAN);
        return ResponseEntity.ok(new DataDto<>(isChange));*/
        return null;
    }
}
