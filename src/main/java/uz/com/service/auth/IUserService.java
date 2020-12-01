package uz.com.service.auth;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.auth.UserCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.*;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IUserService extends IAbstractService {

    ResponseEntity<DataDto<UserDto>> get(Long id);

    ResponseEntity<DataDto<List<UserDto>>> getAll(UserCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull UserCreateDto dto);

    ResponseEntity<DataDto<UserDto>> update(@NotNull UserUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);

    ResponseEntity<DataDto<UserDto>> attachRolesToUser(@NotNull AttachRoleDto dto);

    ResponseEntity<DataDto<Boolean>> changePassword(@NotNull ChangePasswordDto dto);

    ResponseEntity<DataDto<UserDto>> getDetails();
}
