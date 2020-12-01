package uz.com.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.auth.RoleCreateDto;
import uz.com.dto.auth.RoleDto;
import uz.com.dto.auth.RoleUpdateDto;
import uz.com.hibernate.domain.auth.Role;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {PermissionMapper.class})
@Component
public interface RoleMapper extends BaseMapper<Role, RoleDto, RoleCreateDto, RoleUpdateDto> {

    @Override
    @Mapping(target = "createdDate", source = "auditInfo.createdDate", dateFormat = "dd.MM.yyyy")
    RoleDto toDto(Role entity);

    @Override
    @Mapping(target = "name", source = "translations.name")
    @Mapping(target = "nameEn", source = "translations.nameEn")
    @Mapping(target = "nameRu", source = "translations.nameRu")
    @Mapping(target = "nameUz", source = "translations.nameUz")
    Role fromCreateDto(RoleCreateDto createDto);

    @Override
    @Mapping(target = "name", source = "translations.name")
    @Mapping(target = "nameEn", source = "translations.nameEn")
    @Mapping(target = "nameRu", source = "translations.nameRu")
    @Mapping(target = "nameUz", source = "translations.nameUz")
    Role fromUpdateDto(RoleUpdateDto updateDto);
}
