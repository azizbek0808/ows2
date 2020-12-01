package uz.com.service.main;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.main.ApplicationCriteria;
import uz.com.dto.GenericDto;
import uz.com.dto.main.ApplicationCreateDto;
import uz.com.dto.main.ApplicationDto;
import uz.com.dto.main.ApplicationUpdateDto;
import uz.com.exception.ValidatorException;
import uz.com.hibernate.base.PageStream;
import uz.com.hibernate.dao.ApplicationDao;
import uz.com.hibernate.domain.application.Application;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.main.ApplicationMapper;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "applicationService")
public class ApplicationService implements IApplicationService {

    /**
     * Common logger for use in subclasses.
     */
    private final Log logger = LogFactory.getLog(getClass());
    private final ApplicationDao applicationDao;
    private final ApplicationMapper applicationMapper;
    private final GenericMapper genericMapper;
    private final BaseUtils utils;

    @Autowired
    public ApplicationService(ApplicationDao applicationDao, ApplicationMapper applicationMapper, GenericMapper genericMapper, BaseUtils utils) {
        this.applicationDao = applicationDao;
        this.applicationMapper = applicationMapper;
        this.genericMapper = genericMapper;
        this.utils = utils;
    }

    @Override
    public ResponseEntity<DataDto<ApplicationDto>> get(Long id) {
        Optional<Application> optional = applicationDao.search(ApplicationCriteria.childBuilder().selfId(id).build()).stream().findFirst();
        if (!optional.isPresent())
            throw new RuntimeException(String.format("Application with id '%s' not found", id));
        return new ResponseEntity<>(new DataDto<>(applicationMapper.toDto(optional.get())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<List<ApplicationDto>>> getAll(ApplicationCriteria criteria) {
        PageStream<Application> pageStream = applicationDao.search(criteria);
        return new ResponseEntity<>(new DataDto<>(applicationMapper.toDto(pageStream.stream().collect(Collectors.toList())), pageStream.getSize()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull ApplicationCreateDto dto) {
        Application application = applicationMapper.fromCreateDto(dto);
        applicationDao.save(application);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(application)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<ApplicationDto>> update(@NotNull ApplicationUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            throw new ValidatorException("Application id should not be null !!!");

        Application application = (Application) utils.parseJson(applicationDao.get(dto.getId()), new Gson().toJson(dto));
        applicationDao.save(application);

        return get(application.getId());
    }

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        Optional<Application> optional = applicationDao.search(ApplicationCriteria.childBuilder().selfId(id).build()).stream().findFirst();
        if (!optional.isPresent())
            throw new RuntimeException(String.format("Application with id '%s' not found", id));

        applicationDao.delete(optional.get());

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }
}
