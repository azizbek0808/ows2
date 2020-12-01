package uz.com.service.organization;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.criteria.organization.OrganizationMfoCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.*;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IOrganizationMfoService extends IAbstractService {

    ResponseEntity<DataDto<OrganizationMfoDto>> get(Long id);

    ResponseEntity<DataDto<List<OrganizationMfoDto>>> getAll(OrganizationMfoCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull OrganizationMfoCreateDto dto);

    ResponseEntity<DataDto<OrganizationMfoDto>> update(@NotNull OrganizationMfoUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);
}
