package uz.com.service.settings;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.settings.ErrorMessageCriteria;
import uz.com.dto.settings.ErrorMessageDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IErrorMessageService extends IAbstractService {

    ResponseEntity<DataDto<ErrorMessageDto>> get(Long id);

    ResponseEntity<DataDto<List<ErrorMessageDto>>> getAll(ErrorMessageCriteria criteria);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);

}
