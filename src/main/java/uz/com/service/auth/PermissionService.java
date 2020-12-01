package uz.com.service.auth;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.auth.PermissionCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.TranslationDto;
import uz.com.dto.auth.PermissionCreateDto;
import uz.com.dto.auth.PermissionDto;
import uz.com.dto.auth.PermissionUpdateDto;
import uz.com.enums.State;
import uz.com.hibernate.dao.auth.PermissionDao;
import uz.com.hibernate.domain.auth.Permission;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.auth.PermissionMapper;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "permissionService")
public class PermissionService implements IPermissionService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final PermissionDao permissionDao;
    private final PermissionMapper permissionMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;

    @Autowired
    public PermissionService(PermissionDao permissionDao, PermissionMapper permissionMapper, GenericMapper genericMapper, BaseUtils utils) {
        this.permissionDao = permissionDao;
        this.permissionMapper = permissionMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PERMISSION_READ)")
    public ResponseEntity<DataDto<PermissionDto>> get(Long id) {
        Permission permission = permissionDao.get(id);
        if (utils.isEmpty(permission)) {
            logger.error(String.format("Permission with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Permission with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == permission.getState()) {
            logger.error(String.format("Permission with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Permission with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(permissionMapper.toDto(permission)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PERMISSION_READ)")
    public ResponseEntity<DataDto<List<PermissionDto>>> getAll(PermissionCriteria criteria) {
        Long total = permissionDao.total(criteria);
        Stream<Permission> stream = permissionDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(permissionMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PERMISSION_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull PermissionCreateDto dto) {
        utils.fillTranslations(dto.getTranslations(), "Permission");
        if (dto.getParentId() != null && dto.getParentId() == 0)
            dto.setParentId(null);
        Permission permission = permissionMapper.fromCreateDto(dto);
        if (utils.isEmpty(permission.getCode()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Permission code name should not null").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(permissionDao.findByCode(dto.getCode()))) {
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Permission with this code name %s already exist", dto.getCode())).build()), HttpStatus.CONFLICT);
        }
        permission.setCode(permission.getCode().toUpperCase());
        permission.setId(permissionDao.save(permission).getId());
        if (utils.isEmpty(permission.getId())) {
            throw new RuntimeException("Runtime exception Permission Create");
        }
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(permission)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PERMISSION_UPDATE)")
    public ResponseEntity<DataDto<PermissionDto>> update(@NotNull PermissionUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Permission id should not null").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(dto.getCode()))
            dto.setCode(dto.getCode().toUpperCase());
        String jsonString = new Gson().toJson(dto);
        Permission oldPermission = permissionDao.get(dto.getId());
        if (utils.isEmpty(oldPermission)) {
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Permission not found with this id %s", dto.getId())).build()), HttpStatus.BAD_REQUEST);
        }
        Permission permission = (Permission) utils.parseJson(oldPermission, jsonString);
        if (!utils.isEmpty(dto.getTranslations())) {
            TranslationDto translationDto = dto.getTranslations();
            if (!utils.isEmpty(translationDto.getName()))
                permission.setName(translationDto.getName());
            if (!utils.isEmpty(translationDto.getNameEn()))
                permission.setNameEn(translationDto.getNameEn());
            if (!utils.isEmpty(translationDto.getNameRu()))
                permission.setNameRu(translationDto.getNameRu());
            if (!utils.isEmpty(translationDto.getNameUz()))
                permission.setNameUz(translationDto.getNameUz());
        }
        permissionDao.save(permission);
        return get(permission.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).PERMISSION_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        Permission permission = permissionDao.get(id);
        if (utils.isEmpty(permission))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Permission with id '%s' not found", id)).build()), HttpStatus.BAD_REQUEST);
        if (State.DELETED == permission.getState())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Permission already deleted with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        permissionDao.delete(permission);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }
}
