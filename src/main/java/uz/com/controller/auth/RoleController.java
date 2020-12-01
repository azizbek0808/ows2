package uz.com.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.auth.RoleCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.AttachPermissionRoleDto;
import uz.com.dto.auth.RoleCreateDto;
import uz.com.dto.auth.RoleDto;
import uz.com.dto.auth.RoleUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.auth.IRoleService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Role controller")
@RestController
public class RoleController extends ApiController<IRoleService> {
    public RoleController(IRoleService service) {
        super(service);
    }

    @ApiOperation(value = "Get role")
    @RequestMapping(value = API_PATH + V_1 + "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<RoleDto>> getRole(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get all roles")
    @RequestMapping(value = API_PATH + V_1 + "/roles", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<RoleDto>>> getAllRoles(@Valid RoleCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Create role")
    @RequestMapping(value = API_PATH + V_1 + "/roles/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createRole(@RequestBody RoleCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Update role")
    @RequestMapping(value = API_PATH + V_1 + "/roles/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<RoleDto>> updateRole(@RequestBody RoleUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Delete role")
    @RequestMapping(value = API_PATH + V_1 + "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteRole(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @ApiOperation(value = "Attach permission to role")
    @RequestMapping(value = API_PATH + V_1 + "/roles/attach", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> attachPermissions(@RequestBody AttachPermissionRoleDto dto) {
        return service.attachPermissionsToRole(dto);
    }
}
