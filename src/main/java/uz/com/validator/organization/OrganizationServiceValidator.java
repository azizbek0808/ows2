package uz.com.validator.organization;

import org.springframework.stereotype.Component;
import uz.com.dto.CrudDto;
import uz.com.dto.organization.OrganizationCreateDto;
import uz.com.dto.organization.OrganizationUpdateDto;
import uz.com.enums.State;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.settings.Type;
import uz.com.utils.BaseUtils;
import uz.com.validator.BaseCrudValidator;

@Component
public class OrganizationServiceValidator extends BaseCrudValidator<Organization, OrganizationCreateDto, OrganizationUpdateDto> {

    public OrganizationServiceValidator(BaseUtils utils, ErrorMessageDao dao) {
        super(utils, dao);
    }

    @Override
    public void baseValidation(CrudDto domain) {

    }

    @Override
    public void baseValidation(Organization domain, boolean idRequired) {

    }

}
