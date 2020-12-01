package uz.com.validator;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import uz.com.dto.CrudDto;
import uz.com.dto.settings.ErrorMessageCreateDto;
import uz.com.dto.settings.ErrorMessageTranslationCreateDto;
import uz.com.dto.settings.ErrorMessageUpdateDto;
import uz.com.enums.ErrorCodes;
import uz.com.enums.LanguageType;
import uz.com.exception.IdRequiredException;
import uz.com.exception.RequestObjectNullPointerException;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.domain.settings.ErrorMessage;
import uz.com.utils.BaseUtils;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ErrorMessageServiceValidator extends BaseCrudValidator<ErrorMessage, ErrorMessageCreateDto, ErrorMessageUpdateDto> {

    public ErrorMessageServiceValidator(BaseUtils utils, ErrorMessageDao dao) {
        super(utils, dao);
    }

    @Override
    public void baseValidation(CrudDto domain) {

    }


    @SneakyThrows
    @Override
    public void baseValidation(ErrorMessage domain, boolean idRequired){
        if (utils.isEmpty(domain)) {
            throw new RequestObjectNullPointerException(dao.getErrorMessage(ErrorCodes.OBJECT_IS_NULL, utils.toErrorParams(ErrorMessage.class)), "errorMessage");
        } else if (idRequired && utils.isEmpty(domain.getId())) {
            throw new IdRequiredException(dao.getErrorMessage(ErrorCodes.ID_REQUIRED, ""), "id");
        } else if (utils.isEmpty(domain.getErrorCode())) {
            throw new ValidationException(dao.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("errorCode", ErrorMessage.class)), "errorCode");
        } else if (utils.isEmpty(domain.getTranslations()) || domain.getTranslations().size() < 1) {
            throw new ValidationException(dao.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("translations", ErrorMessage.class)), "translations");
        }
    }

    public void validateForTranslations(List<ErrorMessageTranslationCreateDto> dto) throws ValidationException {
        AtomicBoolean validated = new AtomicBoolean(false);
        dto.forEach(object -> {
            if (LanguageType.RU.name().equalsIgnoreCase(object.getLangCode())) {
                validated.set(true);
            }
        });

        if (!validated.get())
            throw new ValidationException(dao.getErrorMessage(ErrorCodes.RUSSIAN_LANGUAGE_SHOULD_NOT_BE_NULL, ""), "atomicBoolean");
    }

}
