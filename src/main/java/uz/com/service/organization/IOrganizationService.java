package uz.com.service.organization;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.OrganizationCreateDto;
import uz.com.dto.organization.OrganizationDto;
import uz.com.dto.organization.OrganizationUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IOrganizationService extends IAbstractService {

    ResponseEntity<DataDto<OrganizationDto>> get(Long id);

    ResponseEntity<DataDto<List<OrganizationDto>>> getAll(OrganizationCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull OrganizationCreateDto dto);

    ResponseEntity<DataDto<OrganizationDto>> update(@NotNull OrganizationUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);
}
