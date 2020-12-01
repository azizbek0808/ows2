package uz.com.hibernate.dao.organization;

import uz.com.criteria.organization.OrganizationAddressCriteria;
import uz.com.dto.organization.OrganizationAddressUpdateDto;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.organization.OrganizationAddress;

public interface OrganizationAddressDao extends Dao<OrganizationAddress, OrganizationAddressCriteria> {
    OrganizationAddress findByName(String name);

    OrganizationAddress findByNameNotId(OrganizationAddressUpdateDto dto);
}
