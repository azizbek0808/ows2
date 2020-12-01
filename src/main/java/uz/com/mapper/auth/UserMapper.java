package uz.com.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.com.dto.auth.UserCreateDto;
import uz.com.dto.auth.UserDto;
import uz.com.dto.auth.UserUpdateDto;
import uz.com.hibernate.domain.auth.User;
import uz.com.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {RoleMapper.class})
@Component
public interface UserMapper extends BaseMapper<User, UserDto, UserCreateDto, UserUpdateDto> {

    @Override
    UserDto toDto(User entity);
}
