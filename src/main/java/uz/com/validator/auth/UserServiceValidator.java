package uz.com.validator.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.com.dto.auth.AttachRoleDto;
import uz.com.dto.auth.AuthUserDto;
import uz.com.dto.auth.ChangePasswordDto;
import uz.com.enums.State;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.domain.auth.User;
import uz.com.utils.BaseUtils;

@Component
public class UserServiceValidator {

    private final BaseUtils utils;

    @Autowired
    public UserServiceValidator(BaseUtils utils) {
        this.utils = utils;
    }

    public void validateUserActive(User user, Long id) {
        if (user == null)
            throw new RuntimeException(String.format("User with id '%s' not found", id));
        if (State.DELETED == user.getState())
            throw new RuntimeException(String.format("User with id '%s' is deleted", id));
        if (user.isLocked())
            throw new RuntimeException(String.format("User with id '%s' is locked", id));
    }

    public void validateOnAuth(AuthUserDto authUserDto) {
        /*if (utils.isEmpty(authUserDto.getUserName()))
            throw new ValidationException(repository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("userName", User.class)), "userName");

        if (utils.isEmpty(authUserDto.getPassword()))
            throw new ValidationException(repository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("password", User.class)), "password");*/

    }

    public void validateChangePassword(ChangePasswordDto dto) {
        if (utils.isEmpty(dto.getUserId()))
            throw new ValidatorException("User id is required !!!");
        if (utils.isEmpty(dto.getNewPassword()))
            throw new ValidatorException("New password is required !!!");
    }

    public void validateOnAttach(AttachRoleDto dto) {
        if (utils.isEmpty(dto.getUserId()))
            throw new ValidatorException("User id is required !!!");
        if (utils.isEmpty(dto.getRoles()))
            throw new ValidatorException("Roles is required !!!");
    }
}
