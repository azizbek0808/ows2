package uz.com.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.criteria.main.ApplicationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.main.ApplicationCreateDto;
import uz.com.dto.main.ApplicationDto;
import uz.com.dto.main.ApplicationUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.main.IApplicationService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Application controller", description = "Zayavkalar")
@RestController
public class ApplicationController extends ApiController<IApplicationService> {
    public ApplicationController(IApplicationService service){
        super(service);
    }

    @ApiOperation(value = "Zayavka ma'lumotlarini olish")
    @RequestMapping(value = API_PATH + V_1 + "/applications/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<ApplicationDto>> getApplication(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Zayavkalar ro'yhatini olish")
    @RequestMapping(value = API_PATH + V_1 + "/applications", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<ApplicationDto>>> getAllApplication(@Valid ApplicationCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Zayavka qo'shish")
    @RequestMapping(value = API_PATH + V_1 + "/applications/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createApplication(@RequestBody ApplicationCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Zayavkani tahrirlash")
    @RequestMapping(value = API_PATH + V_1 + "/applications/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<ApplicationDto>> updateApplication(@RequestBody ApplicationUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Zayavkani o'chirish")
    @RequestMapping(value = API_PATH + V_1 + "/applications/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteApplication(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }
}
