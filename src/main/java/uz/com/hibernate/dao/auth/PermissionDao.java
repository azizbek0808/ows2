package uz.com.hibernate.dao.auth;

import uz.com.criteria.auth.PermissionCriteria;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.auth.Permission;

public interface PermissionDao extends Dao<Permission, PermissionCriteria> {

    Permission findByCode(String code);

}
