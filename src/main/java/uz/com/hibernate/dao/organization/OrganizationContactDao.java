package uz.com.hibernate.dao.organization;

import uz.com.criteria.organization.OrganizationContactCriteria;
import uz.com.dto.organization.OrganizationContactUpdateDto;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.organization.OrganizationContact;

public interface OrganizationContactDao extends Dao<OrganizationContact, OrganizationContactCriteria> {
    OrganizationContact findByEmail(String email);

    OrganizationContact findByEmailNotId(OrganizationContactUpdateDto dto);
}
