package uz.com.service.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.com.criteria.settings.ErrorMessageCriteria;
import uz.com.dto.settings.ErrorMessageDto;
import uz.com.enums.State;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.dao.settings.LanguageRepository;
import uz.com.hibernate.domain.settings.ErrorMessage;
import uz.com.mapper.GenericMapper;
import uz.com.mapper.settings.ErrorMessageMapper;
import uz.com.mapper.settings.LanguageMapper;
import uz.com.response.DataDto;
import uz.com.utils.BaseUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "errorMessageService")
public class ErrorMessageService implements IErrorMessageService {
    private final Log logger = LogFactory.getLog(getClass());
    private final GenericMapper genericMapper;
    private final ErrorMessageDao errorMessageDao;
    private final ErrorMessageMapper errorMessageMapper;
    private final BaseUtils utils;
    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    public ErrorMessageService(GenericMapper genericMapper, ErrorMessageDao errorMessageDao, ErrorMessageMapper errorMessageMapper, BaseUtils utils, LanguageRepository languageRepository, LanguageMapper languageMapper) {
        this.genericMapper = genericMapper;
        this.errorMessageDao = errorMessageDao;
        this.errorMessageMapper = errorMessageMapper;
        this.utils = utils;
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
    }

    @Override
    public ResponseEntity<DataDto<ErrorMessageDto>> get(Long id) {
        ErrorMessage errorMessage = errorMessageDao.get(id);
        if (errorMessage == null)
            throw new RuntimeException(String.format("Error message with id '%s' not found", id));
        if (State.DELETED == errorMessage.getState())
            throw new RuntimeException(String.format("Error message with id '%s' is deleted", id));
        return new ResponseEntity<>(new DataDto<>(errorMessageMapper.toDto(errorMessage)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<List<ErrorMessageDto>>> getAll(ErrorMessageCriteria criteria) {
        Long total = errorMessageDao.total(criteria);
        Stream<ErrorMessage> stream = errorMessageDao.list(criteria);
        return new ResponseEntity<>(new DataDto<>(errorMessageMapper.toDto(stream.collect(Collectors.toList())), total), HttpStatus.OK);
    }

    /*@Override
    public ResponseEntity<DataDto<GenericDto>> create(@NotNull ErrorMessageCreateDto dto) {
        ErrorMessage errorMessage = errorMessageMapper.fromCreateDto(dto);
        if (utils.isEmpty(errorMessage.getErrorCode()))
            throw new ValidatorException("Error message code should not be null !!!");
        if (utils.isEmpty(dto.getTranslations())) {
            throw new ValidatorException("Error message translation should not be null !!!");
        }
        errorMessage.getTranslations().forEach(errorMessageTranslation -> {
            Language lang = languageRepository.findByCode(errorMessageTranslation.getLangCode());
            if (!utils.isEmpty(lang)) {
                errorMessageTranslation.setLanguage(lang);
            }
        });
        errorMessage.setErrorCode(errorMessage.getErrorCode().toUpperCase());
        errorMessageDao.save(errorMessage);
        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(errorMessage)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DataDto<ErrorMessageDto>> update(@NotNull ErrorMessageUpdateDto dto) {
        if (utils.isEmpty(dto.getId()))
            throw new ValidatorException("Error code id should not be null !!!");
        if (!utils.isEmpty(dto.getErrorCode()))
            dto.setErrorCode(dto.getErrorCode().toUpperCase());
        ErrorMessage errorMessage = errorMessageMapper.fromUpdateDto(dto);
        if (utils.isEmpty(dto.getTranslations())) {
            throw new ValidatorException("Error message translation should not be null !!!");
        }
        List<ErrorMessageTranslationCreateDto> errorMessageCreateDtos = dto.getTranslations();
        *//*errorMessageCreateDtos.forEach(errorMessageTranslationCreateDto -> {
            Language lang = languageRepository.findByCode(errorMessageTranslationCreateDto.getLangCode());
            if (!utils.isEmpty(lang)) {
                errorMessageTranslationCreateDto.setLanguageDto(languageMapper.toDto(lang));
            }
        });*//*
        errorMessage.setErrorCode(errorMessage.getErrorCode().toUpperCase());
        errorMessageDao.save(errorMessage);
        return get(errorMessage.getId());
    }*/

    @Override
    public ResponseEntity<DataDto<Boolean>> delete(@NotNull Long id) {
        ErrorMessage errorMessage = errorMessageDao.get(id);
        if (errorMessage == null)
            throw new RuntimeException(String.format("Error message with id '%s' not found", id));
        if (State.DELETED == errorMessage.getState())
            throw new RuntimeException(String.format("Error message id '%s' already deleted", id));
        errorMessageDao.delete(errorMessage);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }


}
