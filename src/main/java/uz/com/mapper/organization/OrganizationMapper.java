package uz.com.mapper.organization;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.organization.OrganizationCreateDto;
import uz.com.dto.organization.OrganizationDto;
import uz.com.dto.organization.OrganizationUpdateDto;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationAddress;
import uz.com.hibernate.domain.organization.OrganizationContact;
import uz.com.hibernate.domain.organization.OrganizationMfo;
import uz.com.mapper.BaseMapper;
import uz.com.mapper.settings.TypeMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {TypeMapper.class})
@Component
public interface OrganizationMapper extends BaseMapper<Organization, OrganizationDto, OrganizationCreateDto, OrganizationUpdateDto> {

    @Override
    @Mapping(target = "financierUser.roles", ignore = true)
    OrganizationDto toDto(Organization entity);


    @Override
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "mfos", ignore = true)
    Organization fromCreateDto(OrganizationCreateDto createDto);
}
