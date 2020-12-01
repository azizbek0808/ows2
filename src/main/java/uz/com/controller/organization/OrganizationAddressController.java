package uz.com.controller.organization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.organization.OrganizationAddressCriteria;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.*;
import uz.com.hibernate.domain.organization.OrganizationAddress;
import uz.com.response.DataDto;
import uz.com.service.organization.IOrganizationAddressService;
import uz.com.service.organization.IOrganizationService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Organization Address controller", description = "Organization Address")
@RestController
public class OrganizationAddressController extends ApiController<IOrganizationAddressService> {
    public OrganizationAddressController(IOrganizationAddressService service) {
        super(service);
    }

    @ApiOperation(value = "Organization Address ma'lumotlarini olish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/addresses{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<OrganizationAddressDto>> getOrganizationAddress(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Organization Address ro'yhatini olish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/addresses", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<OrganizationAddressDto>>> getAllOrganizationAddress(@Valid OrganizationAddressCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Organization Address o'chirish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/addresses/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteOrganizationAddress(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

}
