package uz.com.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.auth.UserCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.*;
import uz.com.response.DataDto;
import uz.com.service.auth.IUserService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "User controller", description = "Foydalanuvchilar")
@RestController
public class UserController extends ApiController<IUserService> {
    public UserController(IUserService service) {
        super(service);
    }

    @ApiOperation(value = "Foydalanuvchi ma'lumotlarini olish")
    @RequestMapping(value = API_PATH + V_1 + "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<UserDto>> getUser(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Foydalanuvchilar ro'yhatini olish")
    @RequestMapping(value = API_PATH + V_1 + "/users", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<UserDto>>> getAllUsers(@Valid UserCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Foydalanuvchi qo'shish")
    @RequestMapping(value = API_PATH + V_1 + "/users/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createUser(@RequestBody UserCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Foydalanuvchini tahrirlash")
    @RequestMapping(value = API_PATH + V_1 + "/users/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<UserDto>> updateUser(@RequestBody UserUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Foydalanuvchini o'chirish")
    @RequestMapping(value = API_PATH + V_1 + "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteUser(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @ApiOperation(value = "Foydalanuvchini ro'lga biriktirish")
    @RequestMapping(value = API_PATH + V_1 + "/users/attach/role", method = RequestMethod.POST)
    public ResponseEntity<DataDto<UserDto>> attachRoles(@RequestBody AttachRoleDto dto) {
        return service.attachRolesToUser(dto);
    }

    @ApiOperation(value = "Foydalanuvchini parolini almashtirish")
    @RequestMapping(value = API_PATH + V_1 + "/users/change/password", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> changePassword(@RequestBody ChangePasswordDto dto) {
        return service.changePassword(dto);
    }

    @RequestMapping(value = API_PATH + V_1 + "/users/me", method = RequestMethod.GET)
    public ResponseEntity<DataDto<UserDto>> getDetails(){
        return service.getDetails();
    }
}
