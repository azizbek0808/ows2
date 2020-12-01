package uz.com.service.main;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.main.ApplicationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.main.ApplicationCreateDto;
import uz.com.dto.main.ApplicationDto;
import uz.com.dto.main.ApplicationUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IApplicationService extends IAbstractService {

    ResponseEntity<DataDto<ApplicationDto>> get(Long id);

    ResponseEntity<DataDto<List<ApplicationDto>>> getAll(ApplicationCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull ApplicationCreateDto dto);

    ResponseEntity<DataDto<ApplicationDto>> update(@NotNull ApplicationUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);
}
