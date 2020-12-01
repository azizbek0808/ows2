package uz.com.controller.organization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.OrganizationCreateDto;
import uz.com.dto.organization.OrganizationDto;
import uz.com.dto.organization.OrganizationUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.organization.IOrganizationService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Organization controller", description = "Organizations")
@RestController
public class OrganizationController extends ApiController<IOrganizationService> {
    public OrganizationController(IOrganizationService service) {
        super(service);
    }

    @ApiOperation(value = "Organization ma'lumotlarini olish")
    @RequestMapping(value = API_PATH + V_1 + "/organizations/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<OrganizationDto>> getOrganization(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Organization ro'yhatini olish")
    @RequestMapping(value = API_PATH + V_1 + "/organizations", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<OrganizationDto>>> getAllOrganizations(@Valid OrganizationCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Organizationni qo'shish")
    @RequestMapping(value = API_PATH + V_1 + "/organizations/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createOrganization(@RequestBody OrganizationCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Organizationni tahrirlash")
    @RequestMapping(value = API_PATH + V_1 + "/organizations/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<OrganizationDto>> updateOrganization(@RequestBody OrganizationUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Organizationni o'chirish")
    @RequestMapping(value = API_PATH + V_1 + "/Organizations/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteOrganization(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

}
