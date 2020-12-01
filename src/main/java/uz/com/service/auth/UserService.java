package uz.com.service.auth;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.com.criteria.auth.UserCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.*;
import uz.com.enums.State;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.dao.auth.RoleDao;
import uz.com.hibernate.dao.auth.UserDao;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.domain.auth.Role;
import uz.com.hibernate.domain.auth.User;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.auth.UserMapper;
import uz.com.repository.IGeneralRepository;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.utils.UserSession;
import uz.com.validator.auth.UserServiceValidator;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "userService")
public class UserService implements IUserService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserSession userSession;
    private final UserMapper userMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final UserServiceValidator validator;
    private final ErrorMessageDao errorMessageDao;
    private final IGeneralRepository generalRepository;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, UserSession userSession, UserMapper userMapper, GenericMapper genericMapper, BaseUtils utils, UserServiceValidator validator, ErrorMessageDao errorMessageDao, IGeneralRepository generalRepository) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userSession = userSession;
        this.userMapper = userMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
        this.errorMessageDao = errorMessageDao;
        this.generalRepository = generalRepository;
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_READ)")
    public ResponseEntity<DataDto<UserDto>> get(Long id) {
        User user = userDao.get(id);
        validator.validateUserActive(user, id);
        return new ResponseEntity<>(new DataDto<>(userMapper.toDto(user)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_READ)")
    public ResponseEntity<DataDto<List<UserDto>>> getAll(UserCriteria criteria) {
        Long total = userDao.total(criteria);
        Stream<User> stream = userDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(userMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull UserCreateDto dto) {
        User user = userMapper.fromCreateDto(dto);
        if (user.getPassword() != null)
            user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));

        userDao.save(user);

        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(user)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_UPDATE)")
    public ResponseEntity<DataDto<UserDto>> update(@NotNull UserUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            throw new ValidatorException("User id should not be null !!!");

        User user = (User) utils.parseJson(userDao.get(dto.getId()), new Gson().toJson(dto));
        userDao.save(user);

        return get(user.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        User user = userDao.get(id);
        if (user == null)
            throw new RuntimeException(String.format("User with id '%s' not found", id));
        if (State.DELETED == user.getState())
            throw new RuntimeException(String.format("User with id '%s' already deleted", id));

        userDao.delete(user);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_ATTACH_ROLE)")
    public ResponseEntity<DataDto<UserDto>> attachRolesToUser(@NotNull AttachRoleDto dto) {
        validator.validateOnAttach(dto);

        User user = userDao.get(dto.getUserId());
        validator.validateUserActive(user, dto.getUserId());

        List<Role> roles = new ArrayList<>();
        dto.getRoles().forEach(genericDto -> {
            Role role = roleDao.get(genericDto.getId());
            if (role == null)
                throw new RuntimeException(String.format("Role with id '%s' not found", genericDto.getId()));
            if (State.DELETED == role.getState())
                throw new RuntimeException(String.format("Role with id '%s' is deleted", genericDto.getId()));

            roles.add(role);
        });

        user.setRoles(roles);
        userDao.save(user);

        return get(user.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).USER_CHANGE_PASSWORD)")
    public ResponseEntity<DataDto<Boolean>> changePassword(@NotNull ChangePasswordDto dto) {
        validator.validateChangePassword(dto);

        User user = userDao.get(dto.getUserId());
        validator.validateUserActive(user, dto.getUserId());

        user.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
        userDao.save(user);

        return ResponseEntity.ok(new DataDto<>(true));
    }

    @Override
    public ResponseEntity<DataDto<UserDto>> getDetails() {
        User user = userDao.get(userSession.getUser().getId());
        UserDto dto = userMapper.toDto(user);
        return new ResponseEntity<>(new DataDto<>(dto), HttpStatus.OK);
    }

}
