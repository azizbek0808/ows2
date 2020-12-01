package uz.com.service.auth;

import org.springframework.http.ResponseEntity;
import uz.com.dto.auth.AuthUserDto;
import uz.com.dto.auth.AuthUserRefreshTokenDto;
import uz.com.dto.auth.ChangePasswordDto;
import uz.com.dto.auth.SessionDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.servlet.http.HttpServletRequest;

public interface IAuthService extends IAbstractService {
    ResponseEntity<DataDto<SessionDto>> login(AuthUserDto user, HttpServletRequest request);

    ResponseEntity<DataDto<Boolean>>logout(HttpServletRequest request);

    ResponseEntity<DataDto<SessionDto>> refreshToken(AuthUserRefreshTokenDto user, HttpServletRequest request);

    ResponseEntity<DataDto<Boolean>> changePassword(ChangePasswordDto dto);

}
