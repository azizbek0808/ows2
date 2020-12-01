package uz.com.hibernate.dao.organization;

import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.organization.OrganizationUpdateDto;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.organization.Organization;

public interface OrganizationDao extends Dao<Organization, OrganizationCriteria> {
    Organization findByName(String name);

    Organization findByNick(String nick);

    Organization findByNameNotId(OrganizationUpdateDto dto);

    Organization findByNickNotId(OrganizationUpdateDto dto);
}
