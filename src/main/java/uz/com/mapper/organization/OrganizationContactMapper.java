package uz.com.mapper.organization;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.organization.OrganizationContactCreateDto;
import uz.com.dto.organization.OrganizationContactDto;
import uz.com.dto.organization.OrganizationContactUpdateDto;
import uz.com.hibernate.domain.organization.OrganizationContact;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface OrganizationContactMapper extends BaseMapper<OrganizationContact, OrganizationContactDto, OrganizationContactCreateDto, OrganizationContactUpdateDto> {

    @Override
    OrganizationContactDto toDto(OrganizationContact entity);

    @Override
    OrganizationContact fromCreateDto(OrganizationContactCreateDto createDto);
}
