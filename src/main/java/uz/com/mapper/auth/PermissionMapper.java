package uz.com.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.auth.PermissionCreateDto;
import uz.com.dto.auth.PermissionDto;
import uz.com.dto.auth.PermissionUpdateDto;
import uz.com.hibernate.domain.auth.Permission;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface PermissionMapper extends BaseMapper<Permission, PermissionDto, PermissionCreateDto, PermissionUpdateDto> {

    @Override
    @Mapping(target = "createdDate", source = "auditInfo.createdDate", dateFormat = "dd.MM.yyyy")
    PermissionDto toDto(Permission entity);

    @Override
    @Mapping(target = "name", source = "translations.name")
    @Mapping(target = "nameEn", source = "translations.nameEn")
    @Mapping(target = "nameRu", source = "translations.nameRu")
    @Mapping(target = "nameUz", source = "translations.nameUz")
    Permission fromCreateDto(PermissionCreateDto createDto);

    @Override
    @Mapping(target = "name", source = "translations.name")
    @Mapping(target = "nameEn", source = "translations.nameEn")
    @Mapping(target = "nameRu", source = "translations.nameRu")
    @Mapping(target = "nameUz", source = "translations.nameUz")
    Permission fromUpdateDto(PermissionUpdateDto updateDto);
}
