package uz.com.service.settings;

import org.springframework.http.ResponseEntity;
import uz.com.criteria.settings.TypeCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.settings.TypeCreateDto;
import uz.com.dto.settings.TypeDto;
import uz.com.dto.settings.TypeUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.IAbstractService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ITypeService extends IAbstractService {

    ResponseEntity<DataDto<TypeDto>> get(Long id);

    ResponseEntity<DataDto<List<TypeDto>>> getAll(TypeCriteria criteria);

    ResponseEntity<DataDto<GenericDto>> create(@NotNull TypeCreateDto dto);

    ResponseEntity<DataDto<TypeDto>> update(@NotNull TypeUpdateDto dto);

    ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id);
}
