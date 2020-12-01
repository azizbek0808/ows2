package uz.com.service.organization;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import uz.com.criteria.organization.OrganizationContactCriteria;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.*;
import uz.com.enums.State;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.dao.organization.OrganizationContactDao;
import uz.com.hibernate.dao.organization.OrganizationDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationContact;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.organization.OrganizationContactMapper;
import uz.com.mapper.organization.OrganizationMapper;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.validator.organization.OrganizationContactServiceValidator;
import uz.com.validator.organization.OrganizationServiceValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "organizationContactService")
public class OrganizationContactService implements IOrganizationContactService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final OrganizationContactDao organizationContactDao;
    private final OrganizationContactMapper organizationContactMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final OrganizationContactServiceValidator validator;

    @Autowired
    public OrganizationContactService(OrganizationContactDao organizationContactDao, OrganizationContactMapper organizationContactMapper, GenericMapper genericMapper, BaseUtils utils, OrganizationContactServiceValidator validator) {
        this.organizationContactDao = organizationContactDao;
        this.organizationContactMapper = organizationContactMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_CONTACT_READ)")
    public ResponseEntity<DataDto<OrganizationContactDto>> get(Long id) {
        OrganizationContact organizationContact = organizationContactDao.get(id);
        if (utils.isEmpty(organizationContact)) {
            logger.error(String.format("OrganizationContact with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationContact with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == organizationContact.getState()) {
            logger.error(String.format("OrganizationContact with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationContact with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(organizationContactMapper.toDto(organizationContact)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_CONTACT_READ)")
    public ResponseEntity<DataDto<List<OrganizationContactDto>>> getAll(OrganizationContactCriteria criteria) {
        Long total = organizationContactDao.total(criteria);
        Stream<OrganizationContact> stream = organizationContactDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(organizationContactMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_CONTACT_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull OrganizationContactCreateDto dto) {
        OrganizationContact organizationContact = organizationContactMapper.fromCreateDto(dto);
        validator.validateDomainOnCreate(organizationContact);
        organizationContactDao.save(organizationContact);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(organizationContact)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_ADDRESS_UPDATE)")
    public ResponseEntity<DataDto<OrganizationContactDto>> update(@NotNull OrganizationContactUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("OrganizationContact id should not be null !!!").build()), HttpStatus.BAD_REQUEST);
        OrganizationContact organizationContact = (OrganizationContact) utils.parseJson(organizationContactDao.get(dto.getId()), new Gson().toJson(dto));
        organizationContactDao.save(organizationContact);
        return get(organizationContact.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_CONTACT_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        OrganizationContact organizationContact = organizationContactDao.get(id);
        if (organizationContact == null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationContact not found with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        if (State.DELETED == organizationContact.getState())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationContact already deleted with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        organizationContactDao.delete(organizationContact);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

}
