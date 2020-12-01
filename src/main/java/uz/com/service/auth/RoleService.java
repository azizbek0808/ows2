package uz.com.service.auth;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.auth.RoleCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.TranslationDto;
import uz.com.dto.auth.AttachPermissionRoleDto;
import uz.com.dto.auth.RoleCreateDto;
import uz.com.dto.auth.RoleDto;
import uz.com.dto.auth.RoleUpdateDto;
import uz.com.enums.State;
import uz.com.hibernate.dao.auth.RoleDao;
import uz.com.hibernate.domain.auth.Role;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.auth.RoleMapper;
import uz.com.repository.IGeneralRepository;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "roleService")
public class RoleService implements IRoleService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final RoleDao roleDao;
    private final RoleMapper roleMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final IGeneralRepository generalRepository;

    @Autowired
    public RoleService(RoleDao roleDao, RoleMapper roleMapper, GenericMapper genericMapper, BaseUtils utils, IGeneralRepository generalRepository) {
        this.roleDao = roleDao;
        this.roleMapper = roleMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.generalRepository = generalRepository;
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ROLE_READ)")
    public ResponseEntity<DataDto<RoleDto>> get(Long id) {
        Role role = roleDao.get(id);
        if (utils.isEmpty(role)) {
            logger.error(String.format("Role with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Role with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == role.getState()) {
            logger.error(String.format("Role with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Role with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(roleMapper.toDto(role)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ROLE_READ)")
    public ResponseEntity<DataDto<List<RoleDto>>> getAll(RoleCriteria criteria) {
        Long total = roleDao.total(criteria);
        Stream<Role> stream = roleDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(roleMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ROLE_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull RoleCreateDto dto) {
        utils.fillTranslations(dto.getTranslations(), "Role");
        Role role = roleMapper.fromCreateDto(dto);
        if (utils.isEmpty(role.getCode()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Role code name should not null").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(roleDao.findByCode(dto.getCode())))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Role with this code name %s already exist", dto.getCode())).build()), HttpStatus.CONFLICT);
        role.setCode(role.getCode().toUpperCase());
        role.setId(roleDao.save(role).getId());
        if (utils.isEmpty(role.getId())) {
            throw new RuntimeException("Runtime exception Role Create");
        }
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(role)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ROLE_UPDATE)")
    public ResponseEntity<DataDto<RoleDto>> update(@NotNull RoleUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Role id should not null").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(dto.getCode()))
            dto.setCode(dto.getCode().toUpperCase());
        String jsonString = new Gson().toJson(dto);
        Role oldRole = roleDao.get(dto.getId());
        if (utils.isEmpty(oldRole)) {
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Role not found with this id %s", dto.getId())).build()), HttpStatus.BAD_REQUEST);
        }
        Role role = (Role) utils.parseJson(oldRole, jsonString);
        if (!utils.isEmpty(dto.getTranslations())) {
            TranslationDto translationDto = dto.getTranslations();
            if (!utils.isEmpty(translationDto.getName()))
                role.setName(translationDto.getName());
            if (!utils.isEmpty(translationDto.getNameEn()))
                role.setNameEn(translationDto.getNameEn());
            if (!utils.isEmpty(translationDto.getNameRu()))
                role.setNameRu(translationDto.getNameRu());
            if (!utils.isEmpty(translationDto.getNameUz()))
                role.setNameUz(translationDto.getNameUz());
        }
        roleDao.save(role);
        return get(role.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ROLE_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        Role role = roleDao.get(id);
        if (utils.isEmpty(role))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Role not found with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        if (State.DELETED == role.getState())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Role already deleted with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        roleDao.delete(role);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> attachPermissionsToRole(AttachPermissionRoleDto dto) {
        if (generalRepository.attachRole(dto)) {
            return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new DataDto<>(false), HttpStatus.OK);
        }
    }
}
