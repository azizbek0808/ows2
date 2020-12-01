package uz.com.validator.settings;

import org.springframework.stereotype.Component;
import uz.com.dto.CrudDto;
import uz.com.dto.settings.TypeCreateDto;
import uz.com.dto.settings.TypeUpdateDto;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.domain.settings.Type;
import uz.com.utils.BaseUtils;
import uz.com.validator.BaseCrudValidator;

@Component
public class TypeServiceValidator extends BaseCrudValidator<Type, TypeCreateDto, TypeUpdateDto> {

    public TypeServiceValidator(BaseUtils utils, ErrorMessageDao dao) {
        super(utils, dao);
    }

    @Override
    public void baseValidation(CrudDto domain) {

    }

    @Override
    public void baseValidation(Type domain, boolean idRequired) {

    }
}
