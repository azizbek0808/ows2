package uz.com.hibernate.dao.auth;

import uz.com.criteria.auth.UserCriteria;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.auth.User;

public interface UserDao extends Dao<User, UserCriteria> {

    User findByUsername(String username);
}
