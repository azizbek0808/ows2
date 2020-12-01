package uz.com.mapper.settings;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.com.dto.settings.ErrorMessageCreateDto;
import uz.com.dto.settings.ErrorMessageDto;
import uz.com.dto.settings.ErrorMessageUpdateDto;
import uz.com.hibernate.domain.settings.ErrorMessage;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ErrorMessageMapper extends BaseMapper<ErrorMessage, ErrorMessageDto, ErrorMessageCreateDto, ErrorMessageUpdateDto> {

}
