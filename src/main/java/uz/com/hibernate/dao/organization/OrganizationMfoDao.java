package uz.com.hibernate.dao.organization;

import uz.com.criteria.organization.OrganizationMfoCriteria;
import uz.com.dto.organization.OrganizationMfoUpdateDto;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.organization.OrganizationMfo;

public interface OrganizationMfoDao extends Dao<OrganizationMfo, OrganizationMfoCriteria> {
    OrganizationMfo findByMfo(String mfo);

    OrganizationMfo findByMfoNotId(OrganizationMfoUpdateDto dto);
}
