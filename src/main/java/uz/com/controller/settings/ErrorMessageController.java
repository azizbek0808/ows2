package uz.com.controller.settings;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.controller.ApiController;
import uz.com.criteria.settings.ErrorMessageCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.settings.ErrorMessageCreateDto;
import uz.com.dto.settings.ErrorMessageDto;
import uz.com.dto.settings.ErrorMessageUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.settings.IErrorMessageService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Error Message Controller")
@RestController
public class ErrorMessageController extends ApiController<IErrorMessageService> {
    public ErrorMessageController(IErrorMessageService service) {
        super(service);
    }

    @ApiOperation(value = "Get error message")
    @RequestMapping(value = API_PATH + V_1 + "/error/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<ErrorMessageDto>> getErrorMessage(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get all error messages")
    @RequestMapping(value = API_PATH + V_1 + "/errors", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<ErrorMessageDto>>> getAllErrorMessages(@Valid ErrorMessageCriteria criteria) {
        return service.getAll(criteria);
    }

    /*@ApiOperation(value = "Create error message")
    @RequestMapping(value = API_PATH + V_1 + "/error/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createErrorMessage(@RequestBody ErrorMessageCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Update error message")
    @RequestMapping(value = API_PATH + V_1 + "/error/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<ErrorMessageDto>> updateErrorMessage(@RequestBody ErrorMessageUpdateDto dto) {
        return service.update(dto);
    }*/

    @ApiOperation(value = "Delete error message")
    @RequestMapping(value = API_PATH + V_1 + "/error/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteErrorMessage(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }


}
