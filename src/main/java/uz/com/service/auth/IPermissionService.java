package uz.com.service.auth;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.auth.PermissionCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.PermissionCreateDto;
import uz.com.dto.auth.PermissionDto;
import uz.com.dto.auth.PermissionUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IPermissionService extends IAbstractService {

    ResponseEntity<DataDto<PermissionDto>> get(Long id);

    ResponseEntity<DataDto<List<PermissionDto>>> getAll(PermissionCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull PermissionCreateDto dto);

    ResponseEntity<DataDto<PermissionDto>> update(@NotNull PermissionUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);

}
