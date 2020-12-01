package uz.com.validator.organization;

import org.springframework.stereotype.Component;
import uz.com.dto.CrudDto;
import uz.com.dto.organization.OrganizationContactCreateDto;
import uz.com.dto.organization.OrganizationContactUpdateDto;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.domain.organization.OrganizationContact;
import uz.com.utils.BaseUtils;
import uz.com.validator.BaseCrudValidator;

@Component
public class OrganizationContactServiceValidator extends BaseCrudValidator<OrganizationContact, OrganizationContactCreateDto, OrganizationContactUpdateDto> {

    public OrganizationContactServiceValidator(BaseUtils utils, ErrorMessageDao dao) {
        super(utils, dao);
    }

    @Override
    public void baseValidation(CrudDto domain) {

    }

    @Override
    public void baseValidation(OrganizationContact domain, boolean idRequired) {

    }


}
