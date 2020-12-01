package uz.com.service.organization;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.organization.OrganizationContactCriteria;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.*;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IOrganizationContactService extends IAbstractService {

    ResponseEntity<DataDto<OrganizationContactDto>> get(Long id);

    ResponseEntity<DataDto<List<OrganizationContactDto>>> getAll(OrganizationContactCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull OrganizationContactCreateDto dto);

    ResponseEntity<DataDto<OrganizationContactDto>> update(@NotNull OrganizationContactUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);
}
