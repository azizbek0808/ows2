package uz.com.service.organization;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.criteria.organization.OrganizationMfoCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.*;
import uz.com.enums.State;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.dao.organization.OrganizationDao;
import uz.com.hibernate.dao.organization.OrganizationMfoDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationMfo;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.organization.OrganizationMapper;
import uz.com.mapper.organization.OrganizationMfoMapper;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.validator.organization.OrganizationMfoServiceValidator;
import uz.com.validator.organization.OrganizationServiceValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "organizationMfoService")
public class OrganizationMfoService implements IOrganizationMfoService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final OrganizationMfoDao organizationMfoDao;
    private final OrganizationMfoMapper organizationMfoMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final OrganizationMfoServiceValidator validator;

    @Autowired
    public OrganizationMfoService(OrganizationMfoDao organizationMfoDao, OrganizationMfoMapper organizationMfoMapper, GenericMapper genericMapper, BaseUtils utils, OrganizationMfoServiceValidator validator) {
        this.organizationMfoDao = organizationMfoDao;
        this.organizationMfoMapper = organizationMfoMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_MFO_READ)")
    public ResponseEntity<DataDto<OrganizationMfoDto>> get(Long id) {
        OrganizationMfo organizationMfo = organizationMfoDao.get(id);
        if (utils.isEmpty(organizationMfo)) {
            logger.error(String.format("OrganizationMfo with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationMfo with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == organizationMfo.getState()) {
            logger.error(String.format("OrganizationMfo with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationMfo with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(organizationMfoMapper.toDto(organizationMfo)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_MFO_READ)")
    public ResponseEntity<DataDto<List<OrganizationMfoDto>>> getAll(OrganizationMfoCriteria criteria) {
        Long total = organizationMfoDao.total(criteria);
        Stream<OrganizationMfo> stream = organizationMfoDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(organizationMfoMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_MFO_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull OrganizationMfoCreateDto dto) {
        OrganizationMfo organizationMfo = organizationMfoMapper.fromCreateDto(dto);
        validator.validateDomainOnCreate(organizationMfo);
        organizationMfoDao.save(organizationMfo);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(organizationMfo)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_MFO_UPDATE)")
    public ResponseEntity<DataDto<OrganizationMfoDto>> update(@NotNull OrganizationMfoUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("OrganizationMfo id should not be null !!!").build()), HttpStatus.BAD_REQUEST);
        OrganizationMfo organizationMfo = (OrganizationMfo) utils.parseJson(organizationMfoDao.get(dto.getId()), new Gson().toJson(dto));
        organizationMfoDao.save(organizationMfo);
        return get(organizationMfo.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_MFO_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        OrganizationMfo organizationMfo = organizationMfoDao.get(id);
        if (organizationMfo == null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationMfo not found with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        if (State.DELETED == organizationMfo.getState())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationMfo already deleted with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        organizationMfoDao.delete(organizationMfo);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

}
