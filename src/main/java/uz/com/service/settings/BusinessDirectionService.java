package uz.com.service.settings;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.settings.BusinessDirectionCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.settings.BusinessDirectionCreateDto;
import uz.com.dto.settings.BusinessDirectionDto;
import uz.com.dto.settings.BusinessDirectionUpdateDto;
import uz.com.enums.State;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.dao.settings.BusinessDirectionDao;
import uz.com.hibernate.domain.settings.BusinessDirection;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.settings.BusinessDirectionMapper;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;
import uz.com.validator.settings.BusinessDirectionServiceValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "businessDirectionService")
public class BusinessDirectionService implements IBusinessDirectionService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final BusinessDirectionDao businessDirectionDao;
    private final BusinessDirectionMapper businessDirectionMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;
    private final BusinessDirectionServiceValidator validator;

    @Autowired
    public BusinessDirectionService(BusinessDirectionDao businessDirectionDao, BusinessDirectionMapper businessDirectionMapper, GenericMapper genericMapper, BaseUtils utils, BusinessDirectionServiceValidator validator) {
        this.businessDirectionDao = businessDirectionDao;
        this.businessDirectionMapper = businessDirectionMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
        this.validator = validator;
    }

    @Override
    //   @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).BUSINESS_DIRECTION_READ)")
    public ResponseEntity<DataDto<BusinessDirectionDto>> get(Long id) {
        BusinessDirection businessDirection = businessDirectionDao.get(id);
        return new ResponseEntity<>(new DataDto<>(businessDirectionMapper.toDto(businessDirection)), HttpStatus.OK);
    }

    @Override
    //  @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).BUSINESS_DIRECTION_READ)")
    public ResponseEntity<DataDto<List<BusinessDirectionDto>>> getAll(BusinessDirectionCriteria criteria) {
        Long total = businessDirectionDao.total(criteria);
        Stream<BusinessDirection> stream = businessDirectionDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(businessDirectionMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    @Override
    //    @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).BUSINESS_DIRECTION_CREATE)")
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull BusinessDirectionCreateDto dto) {
        BusinessDirection businessDirection = businessDirectionMapper.fromCreateDto(dto);
        businessDirectionDao.save(businessDirection);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(businessDirection)), HttpStatus.CREATED);
    }

    @Override
    //  @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).BUSINESS_DIRECTION_UPDATE)")
    public ResponseEntity<DataDto<BusinessDirectionDto>> update(@NotNull BusinessDirectionUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            throw new ValidatorException("Business Direction id should not be null !!!");

        BusinessDirection businessDirection = (BusinessDirection) utils.parseJson(businessDirectionDao.get(dto.getId()), new Gson().toJson(dto));
        businessDirectionDao.save(businessDirection);

        return get(businessDirection.getId());
    }

    @Override
    //  @PreAuthorize("hasPermission(null, T(uz.com.enums.Permissions).BUSINESS_DIRECTION_DELETE)")
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        BusinessDirection businessDirection = businessDirectionDao.get(id);
        if (businessDirection == null)
            throw new RuntimeException(String.format("Business Direction with id '%s' not found", id));
        if (State.DELETED == businessDirection.getState())
            throw new RuntimeException(String.format("Business Direction with id '%s' already deleted", id));

        businessDirectionDao.delete(businessDirection);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

}
