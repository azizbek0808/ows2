package uz.com.mapper.settings;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.settings.TypeCreateDto;
import uz.com.dto.settings.TypeDto;
import uz.com.dto.settings.TypeUpdateDto;
import uz.com.hibernate.domain.settings.Type;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface TypeMapper extends BaseMapper<Type, TypeDto, TypeCreateDto, TypeUpdateDto> {

    @Override
    TypeDto toDto(Type entity);
}
