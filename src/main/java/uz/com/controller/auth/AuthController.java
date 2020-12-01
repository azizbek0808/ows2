package uz.com.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.com.controller.ApiController;
import uz.com.dto.auth.AuthUserDto;
import uz.com.dto.auth.AuthUserRefreshTokenDto;
import uz.com.dto.auth.ChangePasswordDto;
import uz.com.dto.auth.SessionDto;
import uz.com.response.DataDto;
import uz.com.service.auth.IAuthService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController extends ApiController<IAuthService> {

    public AuthController(IAuthService service) {
        super(service);
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public ResponseEntity<DataDto<SessionDto>> login(@RequestBody AuthUserDto dto, HttpServletRequest request) {
        return service.login(dto, request);
    }

    @RequestMapping(value = REFRESH_TOKEN_URL, method = RequestMethod.POST)
    public ResponseEntity<DataDto<SessionDto>> refreshToken(@RequestBody AuthUserRefreshTokenDto dto, HttpServletRequest request) {
        return service.refreshToken(dto, request);
    }

    @RequestMapping(value = AUTH + "/change/password", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> changePassword(@RequestBody ChangePasswordDto dto) {
        return service.changePassword(dto);
    }
//    @RequestMapping(value = SIGNIN_OTP_URL,  method = RequestMethod.POST)
//    public ResponseEntity<DataDto<Boolean>> signIn(@RequestParam(value = "userName") String param) {
//        return service.signInOtp(param);
//    }

//    @RequestMapping(value = OTP_CONFIRM_URL, method = RequestMethod.POST)
//    public ResponseEntity<DataDto<SessionDto>> otpConfirm(@RequestBody UserOtpConfirmDto dto) {
//        return service.otpConfirm(dto);
//    }

    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
    public ResponseEntity<DataDto<Boolean>> logout(HttpServletRequest request) {
        return service.logout(request);
    }
}
