package uz.com.hibernate.dao.auth;

import uz.com.criteria.auth.RoleCriteria;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.auth.Role;

public interface RoleDao extends Dao<Role, RoleCriteria> {
    Role findByCode(String code);
}
