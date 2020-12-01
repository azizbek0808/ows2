package uz.com.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.GenericDto;
import uz.com.hibernate.base._Entity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface GenericMapper {
    GenericDto fromDomain(_Entity domain);
}
