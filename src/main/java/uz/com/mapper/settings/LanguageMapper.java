package uz.com.mapper.settings;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.com.dto.GenericCrudDto;
import uz.com.dto.settings.LanguageDto;
import uz.com.hibernate.domain.settings.Language;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LanguageMapper extends BaseMapper<Language, LanguageDto, GenericCrudDto, GenericCrudDto> {
}
