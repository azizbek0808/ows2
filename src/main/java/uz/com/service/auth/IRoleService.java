package uz.com.service.auth;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.auth.RoleCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.*;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IRoleService extends IAbstractService {

    ResponseEntity<DataDto<RoleDto>> get(Long id);

    ResponseEntity<DataDto<List<RoleDto>>> getAll(RoleCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull RoleCreateDto dto);

    ResponseEntity<DataDto<RoleDto>> update(@NotNull RoleUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);

    ResponseEntity<DataDto<Boolean>> attachPermissionsToRole(AttachPermissionRoleDto dto);
}
