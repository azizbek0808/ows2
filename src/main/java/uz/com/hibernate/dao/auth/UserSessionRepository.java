package uz.com.hibernate.dao.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.com.hibernate.domain.auth.User;

@Repository
public interface UserSessionRepository extends CrudRepository<User, Long> {

    User findByUsername(String userName);
}
