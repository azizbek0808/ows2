package uz.com.service.settings;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.settings.BusinessDirectionCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.settings.BusinessDirectionCreateDto;
import uz.com.dto.settings.BusinessDirectionDto;
import uz.com.dto.settings.BusinessDirectionUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IBusinessDirectionService extends IAbstractService {

    ResponseEntity<DataDto<BusinessDirectionDto>> get(Long id);

    ResponseEntity<DataDto<List<BusinessDirectionDto>>> getAll(BusinessDirectionCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull BusinessDirectionCreateDto dto);

    ResponseEntity<DataDto<BusinessDirectionDto>> update(@NotNull BusinessDirectionUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);
}
