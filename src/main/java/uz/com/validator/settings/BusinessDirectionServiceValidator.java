package uz.com.validator.settings;

import org.springframework.stereotype.Component;
import uz.com.dto.CrudDto;
import uz.com.dto.settings.BusinessDirectionCreateDto;
import uz.com.dto.settings.BusinessDirectionUpdateDto;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.domain.settings.BusinessDirection;
import uz.com.utils.BaseUtils;
import uz.com.validator.BaseCrudValidator;

@Component
public class BusinessDirectionServiceValidator extends BaseCrudValidator<BusinessDirection, BusinessDirectionCreateDto, BusinessDirectionUpdateDto> {

    public BusinessDirectionServiceValidator(BaseUtils utils, ErrorMessageDao dao) {
        super(utils, dao);
    }

    @Override
    public void baseValidation(CrudDto domain) {

    }

    @Override
    public void baseValidation(BusinessDirection domain, boolean idRequired) {

    }


}
