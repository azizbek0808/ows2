package uz.com.mapper.organization;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.organization.OrganizationMfoCreateDto;
import uz.com.dto.organization.OrganizationMfoDto;
import uz.com.dto.organization.OrganizationMfoUpdateDto;
import uz.com.hibernate.domain.organization.OrganizationMfo;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface OrganizationMfoMapper extends BaseMapper<OrganizationMfo, OrganizationMfoDto, OrganizationMfoCreateDto, OrganizationMfoUpdateDto> {

    @Override
    OrganizationMfoDto toDto(OrganizationMfo entity);

    @Override
    OrganizationMfo fromCreateDto(OrganizationMfoCreateDto createDto);
}
