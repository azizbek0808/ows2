package uz.com.mapper.organization;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.organization.OrganizationAddressCreateDto;
import uz.com.dto.organization.OrganizationAddressDto;
import uz.com.dto.organization.OrganizationAddressUpdateDto;
import uz.com.hibernate.domain.organization.OrganizationAddress;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface OrganizationAddressMapper extends BaseMapper<OrganizationAddress, OrganizationAddressDto, OrganizationAddressCreateDto, OrganizationAddressUpdateDto> {

    @Override
    OrganizationAddressDto toDto(OrganizationAddress entity);

    @Override
    OrganizationAddress fromCreateDto(OrganizationAddressCreateDto createDto);
}
