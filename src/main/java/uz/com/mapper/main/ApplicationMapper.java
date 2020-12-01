package uz.com.mapper.main;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.main.ApplicationCreateDto;
import uz.com.dto.main.ApplicationDto;
import uz.com.dto.main.ApplicationUpdateDto;
import uz.com.hibernate.domain.application.Application;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface ApplicationMapper extends BaseMapper<Application, ApplicationDto, ApplicationCreateDto, ApplicationUpdateDto> {
}