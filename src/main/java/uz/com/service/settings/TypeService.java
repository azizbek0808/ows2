package uz.com.service.settings;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.settings.TypeCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.settings.TypeCreateDto;
import uz.com.dto.settings.TypeDto;
import uz.com.dto.settings.TypeUpdateDto;
import uz.com.enums.State;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.dao.settings.TypeDao;
import uz.com.hibernate.domain.settings.Type;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.settings.TypeMapper;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.validator.settings.TypeServiceValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "typeService")
public class TypeService implements ITypeService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final TypeDao typeDao;
    private final TypeMapper typeMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final TypeServiceValidator validator;

    @Autowired
    public TypeService(TypeDao typeDao, TypeMapper typeMapper, GenericMapper genericMapper, BaseUtils utils, TypeServiceValidator validator) {
        this.typeDao = typeDao;
        this.typeMapper = typeMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
    }



    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).SETTINGS_TYPE_READ)")
    public ResponseEntity<DataDto<TypeDto>> get(Long id) {
        Type type = typeDao.get(id);
        if (utils.isEmpty(type)) {
            logger.error(String.format("Type with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Type with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == type.getState()) {
            logger.error(String.format("Type with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Type with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(typeMapper.toDto(type)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).SETTINGS_TYPE_READ)")
    public ResponseEntity<DataDto<List<TypeDto>>> getAll(TypeCriteria criteria) {
        Long total = typeDao.total(criteria);
        Stream<Type> stream = typeDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(typeMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
    //    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).SETTINGS_TYPE_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull TypeCreateDto dto) {
        Type type = typeMapper.fromCreateDto(dto);
        validator.validateDomainOnCreate(type);
        if (utils.isEmpty(type.getValue()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Type value should not null").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(typeDao.findByValue(dto.getValue())))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Type with this Value %s already exist", dto.getValue())).build()), HttpStatus.CONFLICT);
        typeDao.save(type);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(type)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).SETTINGS_TYPE_UPDATE)")
    public ResponseEntity<DataDto<TypeDto>> update(@NotNull TypeUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Type id should not be null !!!").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(typeDao.findByValueNotId(dto)))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Type with this value %s already exist", dto.getValue())).build()), HttpStatus.CONFLICT);
        Type type = (Type) utils.parseJson(typeDao.get(dto.getId()), new Gson().toJson(dto));
        typeDao.save(type);
        return get(type.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).SETTINGS_TYPE_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        Type type = typeDao.get(id);
        if (type == null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Type not found with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        if (State.DELETED == type.getState())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Type already deleted with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        typeDao.delete(type);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

}
