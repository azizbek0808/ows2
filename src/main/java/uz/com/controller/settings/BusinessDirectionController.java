package uz.com.controller.settings;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.settings.BusinessDirectionCriteria;
import uz.com.criteria.settings.TypeCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.settings.*;
import uz.com.hibernate.domain.settings.BusinessDirection;
import uz.com.response.DataDto;
import uz.com.service.settings.IBusinessDirectionService;
import uz.com.service.settings.ITypeService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Business Direction controller", description = "Settings Type")
@RestController
public class BusinessDirectionController extends ApiController<IBusinessDirectionService> {
    public BusinessDirectionController(IBusinessDirectionService service) {
        super(service);
    }

    @ApiOperation(value = "Business Direction ma'lumotlarini olish")
    @RequestMapping(value = API_PATH + V_1 + "/business/directions/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<BusinessDirectionDto>> getBusinessDirection(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Business Direction ro'yhatini olish")
    @RequestMapping(value = API_PATH + V_1 + "/business/directions", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<BusinessDirectionDto>>> getAllBusinessDirections(@Valid BusinessDirectionCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Business Directionni qo'shish")
    @RequestMapping(value = API_PATH + V_1 + "/business/directions/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createBusinessDirection(@RequestBody BusinessDirectionCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Business Directionni tahrirlash")
    @RequestMapping(value = API_PATH + V_1 + "/business/directions/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<BusinessDirectionDto>> updateBusinessDirection(@RequestBody BusinessDirectionUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Business Directionni o'chirish")
    @RequestMapping(value = API_PATH + V_1 + "/business/directions/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteBusinessDirection(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

}
