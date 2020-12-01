package uz.com.service.organization;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.organization.OrganizationAddressCriteria;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.*;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IOrganizationAddressService extends IAbstractService {

    ResponseEntity<DataDto<OrganizationAddressDto>> get(Long id);

    ResponseEntity<DataDto<List<OrganizationAddressDto>>> getAll(OrganizationAddressCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull OrganizationAddressCreateDto dto);

    ResponseEntity<DataDto<OrganizationAddressDto>> update(@NotNull OrganizationAddressUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);
}
