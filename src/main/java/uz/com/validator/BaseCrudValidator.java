package uz.com.validator;


import uz.com.dto.CrudDto;
import uz.com.exception.IdRequiredException;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.utils.BaseUtils;

import javax.xml.bind.ValidationException;

import static uz.com.enums.ErrorCodes.ID_REQUIRED;

/**
 * Created by 'javokhir' on 27/06/2019
 */

public abstract class BaseCrudValidator<T, C extends CrudDto, U extends CrudDto> extends AbstractValidator<T> {

    public BaseCrudValidator(BaseUtils utils, ErrorMessageDao dao) {
        super(utils, dao);
    }

    public void validateOnCreate(C domain) {
        baseValidation(domain);
    }

    public void validateDomainOnCreate(T domain) {
        baseValidation(domain, false);
    }

    public void validateOnUpdate(U domain) {
        baseValidation(domain);
    }

    public void validateDomainOnUpdate(T domain)  {
        baseValidation(domain, true);
    }

    public abstract void baseValidation(CrudDto domain);

    public abstract void baseValidation(T domain, boolean idRequired);

    public void validateOnDelete(Long id) {
        if (id == null) {
            throw new IdRequiredException(dao.getErrorMessage(ID_REQUIRED, ""));
        }
    }
}
