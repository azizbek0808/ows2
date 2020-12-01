package uz.com.controller.organization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.com.controller.ApiController;
import uz.com.criteria.organization.OrganizationMfoCriteria;
import uz.com.dto.organization.OrganizationMfoDto;
import uz.com.response.DataDto;
import uz.com.service.organization.IOrganizationMfoService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Organization Mfo controller", description = "Organization Mfo")
@RestController
public class OrganizationMfoController extends ApiController<IOrganizationMfoService> {
    public OrganizationMfoController(IOrganizationMfoService service) {
        super(service);
    }

    @ApiOperation(value = "Organization Mfo ma'lumotlarini olish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/mfos{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<OrganizationMfoDto>> getOrganizationMfo(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Organization Mfo ro'yhatini olish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/mfos", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<OrganizationMfoDto>>> getAllOrganizationContact(@Valid OrganizationMfoCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Organization Mfo o'chirish")
    @RequestMapping(value = API_PATH + V_1 + "/organization/mfos/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteOrganizationMfo(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

}
