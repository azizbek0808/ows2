package uz.com.validator.organization;

import org.springframework.stereotype.Component;
import uz.com.dto.CrudDto;
import uz.com.dto.organization.OrganizationAddressCreateDto;
import uz.com.dto.organization.OrganizationAddressUpdateDto;
import uz.com.dto.organization.OrganizationCreateDto;
import uz.com.dto.organization.OrganizationUpdateDto;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationAddress;
import uz.com.utils.BaseUtils;
import uz.com.validator.BaseCrudValidator;

@Component
public class OrganizationAddressServiceValidator extends BaseCrudValidator<OrganizationAddress, OrganizationAddressCreateDto, OrganizationAddressUpdateDto> {

    public OrganizationAddressServiceValidator(BaseUtils utils, ErrorMessageDao dao) {
        super(utils, dao);
    }

    @Override
    public void baseValidation(CrudDto domain) {

    }

    @Override
    public void baseValidation(OrganizationAddress domain, boolean idRequired) {

    }


}
