package uz.com.service.organization;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.*;
import uz.com.enums.State;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.dao.auth.UserDao;
import uz.com.hibernate.dao.organization.OrganizationDao;
import uz.com.hibernate.dao.settings.BusinessDirectionDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationAddress;
import uz.com.hibernate.domain.organization.OrganizationContact;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.organization.OrganizationMapper;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.validator.organization.OrganizationServiceValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service(value = "organizationService")
public class OrganizationService implements IOrganizationService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final OrganizationDao organizationDao;
    private final OrganizationMapper organizationMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final OrganizationServiceValidator validator;
    private final BusinessDirectionDao businessDirectionDao;
    private final UserDao userDao;
    private final IOrganizationAddressService organizationAddressService;
    private final IOrganizationContactService organizationContactService;
    private final IOrganizationMfoService organizationMfoService;

    @Autowired
    public OrganizationService(OrganizationDao organizationDao, OrganizationMapper organizationMapper, GenericMapper genericMapper, BaseUtils utils, OrganizationServiceValidator validator, BusinessDirectionDao businessDirectionDao, UserDao userDao, IOrganizationAddressService organizationAddressService, IOrganizationContactService organizationContactService, IOrganizationMfoService organizationMfoService) {
        this.organizationDao = organizationDao;
        this.organizationMapper = organizationMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
        this.businessDirectionDao = businessDirectionDao;
        this.userDao = userDao;
        this.organizationAddressService = organizationAddressService;
        this.organizationContactService = organizationContactService;
        this.organizationMfoService = organizationMfoService;
    }


    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_READ)")
    public ResponseEntity<DataDto<OrganizationDto>> get(Long id) {
        Organization organization = organizationDao.get(id);
        if (utils.isEmpty(organization)) {
            logger.error(String.format("Organization with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Organization with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == organization.getState()) {
            logger.error(String.format("Organization with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Organization with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(organizationMapper.toDto(organization)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_READ)")
    public ResponseEntity<DataDto<List<OrganizationDto>>> getAll(OrganizationCriteria criteria) {
        Long total = organizationDao.total(criteria);
        Stream<Organization> stream = organizationDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(organizationMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull OrganizationCreateDto dto) {
        Organization organization = organizationMapper.fromCreateDto(dto);
        organization.setBusinessDirection(businessDirectionDao.get(dto.getBusinessDirectionId()));
        organization.setFinancierUser(userDao.get(dto.getFinancierUserId()));
        validator.validateDomainOnCreate(organization);
        if (utils.isEmpty(organization.getName()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Organization name should not null").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(organizationDao.findByName(dto.getName())))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Organization with this Name %s already exist", dto.getName())).build()), HttpStatus.CONFLICT);
        if (utils.isEmpty(organization.getNick()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Organization nick should not null").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(organizationDao.findByNick(dto.getNick())))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Organization with this nick %s already exist", dto.getName())).build()), HttpStatus.CONFLICT);
        Organization newOrganization = organizationDao.save(organization);

        for (OrganizationAddressCreateDto organizationAddressCreateDto : dto.getAddresses()) {
            organizationAddressCreateDto.setOrganization(newOrganization);
            organizationAddressService.create(organizationAddressCreateDto);
        }

        for (OrganizationContactCreateDto organizationContactCreateDto : dto.getContacts()) {
            organizationContactCreateDto.setOrganization(newOrganization);
            organizationContactService.create(organizationContactCreateDto);
        }

        for (OrganizationMfoCreateDto organizationMfoCreateDto : dto.getMfos()) {
            organizationMfoCreateDto.setOrganization(newOrganization);
            organizationMfoService.create(organizationMfoCreateDto);
        }
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(organization)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_UPDATE)")
    public ResponseEntity<DataDto<OrganizationDto>> update(@NotNull OrganizationUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("Organization id should not be null !!!").build()), HttpStatus.BAD_REQUEST);
        if (!utils.isEmpty(organizationDao.findByNameNotId(dto)))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Organization with this name %s already exist", dto.getName())).build()), HttpStatus.CONFLICT);
        if (!utils.isEmpty(organizationDao.findByNickNotId(dto)))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Organization with this nick %s already exist", dto.getNick())).build()), HttpStatus.CONFLICT);
        Organization organization = (Organization) utils.parseJson(organizationDao.get(dto.getId()), new Gson().toJson(dto));
        organizationDao.save(organization);
        return get(organization.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        Organization organization = organizationDao.get(id);
        if (organization == null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Organization not found with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        if (State.DELETED == organization.getState())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("Organization already deleted with this id %s", id)).build()), HttpStatus.BAD_REQUEST);
        organizationDao.delete(organization);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

}
