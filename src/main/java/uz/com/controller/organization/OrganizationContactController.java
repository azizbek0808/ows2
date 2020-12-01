package uz.com.controller.organization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.com.controller.ApiController;
import uz.com.criteria.organization.OrganizationContactCriteria;
import uz.com.dto.organization.OrganizationContactDto;
import uz.com.response.DataDto;
import uz.com.service.organization.IOrganizationContactService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Organization Contact controller", description = "Organization Contact")
@RestController
public class OrganizationContactController extends ApiController<IOrganizationContactService> {
    public OrganizationContactController(IOrganizationContactService service) {
        super(service);
    }

    @ApiOperation(value = "Organization Contact ma'lumotlarini olish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/contacts{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<OrganizationContactDto>> getOrganizationContact(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Organization Contact ro'yhatini olish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/contacts", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<OrganizationContactDto>>> getAllOrganizationContact(@Valid OrganizationContactCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Organization Contactni o'chirish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/contacts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteOrganizationContact(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

}
