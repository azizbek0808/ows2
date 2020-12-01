package uz.com.service.organization;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.organization.OrganizationAddressCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.organization.OrganizationAddressCreateDto;
import uz.com.dto.organization.OrganizationAddressDto;
import uz.com.dto.organization.OrganizationAddressUpdateDto;
import uz.com.enums.State;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.dao.organization.OrganizationAddressDao;
import uz.com.hibernate.domain.organization.OrganizationAddress;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.organization.OrganizationAddressMapper;
import uz.com.response.AppErrorDto;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.validator.organization.OrganizationAddressServiceValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "OrganizationAddressService")
public class OrganizationAddressService implements IOrganizationAddressService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final OrganizationAddressDao organizationAddressDao;
    private final OrganizationAddressMapper organizationAddressMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final OrganizationAddressServiceValidator validator;

    @Autowired
    public OrganizationAddressService(OrganizationAddressDao organizationAddressDao, OrganizationAddressMapper organizationAddressMapper, GenericMapper genericMapper, BaseUtils utils, OrganizationAddressServiceValidator validator) {
        this.organizationAddressDao = organizationAddressDao;
        this.organizationAddressMapper = organizationAddressMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_ADDRESS_READ)")
    public ResponseEntity<DataDto<OrganizationAddressDto>> get(Long id) {
        OrganizationAddress organizationAddress = organizationAddressDao.get(id);
        if (utils.isEmpty(organizationAddress)) {
            logger.error(String.format("OrganizationAddress with id '%s' not found", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationAddress with id '%s' not found", id)).build()), HttpStatus.NOT_FOUND);
        }
        if (State.DELETED == organizationAddress.getState()) {
            logger.error(String.format("OrganizationAddress with id '%s' is deleted", id));
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(String.format("OrganizationAddress with id '%s' is deleted", id)).build()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DataDto<>(organizationAddressMapper.toDto(organizationAddress)), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_ADDRESS_READ)")
    public ResponseEntity<DataDto<List<OrganizationAddressDto>>> getAll(OrganizationAddressCriteria criteria) {
        Long total = organizationAddressDao.total(criteria);
        Stream<OrganizationAddress> stream = organizationAddressDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(organizationAddressMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_ADDRESS_UPDATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull OrganizationAddressCreateDto dto) {
        OrganizationAddress organizationAddress = organizationAddressMapper.fromCreateDto(dto);
        validator.validateDomainOnCreate(organizationAddress);
        organizationAddressDao.save(organizationAddress);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(organizationAddress)), HttpStatus.CREATED);
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_UPDATE)")
    public ResponseEntity<DataDto<OrganizationAddressDto>> update(@NotNull OrganizationAddressUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage("OrganizationAddress id should not be null !!!").build()), HttpStatus.BAD_REQUEST);
        OrganizationAddress organizationAddress = (OrganizationAddress) utils.parseJson(organizationAddressDao.get(dto.getId()), new Gson().toJson(dto));
        organizationAddressDao.save(organizationAddress);
        return get(organizationAddress.getId());
    }

    @Override
//    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).ORGANIZATION_ADDRESS_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        OrganizationAddress organizationAddress = organizationAddressDao.get(id);
        if (organizationAddress == null)
            throw new RuntimeException(String.format("OrganizationAddress with id '%s' not found", id));
        if (State.DELETED == organizationAddress.getState())
            throw new RuntimeException(String.format("OrganizationAddress with id '%s' already deleted", id));

        organizationAddressDao.delete(organizationAddress);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

}
