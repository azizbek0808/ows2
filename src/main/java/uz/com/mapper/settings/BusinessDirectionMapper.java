package uz.com.mapper.settings;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.settings.BusinessDirectionCreateDto;
import uz.com.dto.settings.BusinessDirectionDto;
import uz.com.dto.settings.BusinessDirectionUpdateDto;
import uz.com.hibernate.domain.settings.BusinessDirection;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface BusinessDirectionMapper extends BaseMapper<BusinessDirection, BusinessDirectionDto, BusinessDirectionCreateDto, BusinessDirectionUpdateDto> {

//    @Override
//    BusinessDirectionDto toDto(BusinessDirection entity);
}
