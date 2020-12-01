package uz.com.hibernate.dao.impl.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.auth.UserCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.auth.UserDao;
import uz.com.hibernate.domain.auth.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Map;
import java.util.stream.Stream;

@Repository("userDao")
public class UserDaoImpl extends DaoImpl<User, UserCriteria> implements UserDao {

    /**
     * Common logger for use in subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    protected EntityManager entityManager;

    @Override
    public Stream<User> list(UserCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM User t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(UserCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM User t WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public User findByUsername(String username) {
        try {
            return (User) entityManager.createQuery("SELECT t FROM User t WHERE t.username = '" + username + "' ORDER BY t.id DESC ").getSingleResult();
        } catch (NoResultException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(String.format("User with username '%s' not found", username));
        }
    }

    private class CustomFilter {
        private final UserCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(UserCriteria criteria) {
            this.criteria = criteria;
        }

        public String getFilterQuery() {
            return filterQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public CustomFilter invoke() {
            filterQuery = "";
            params = preparing();

            if (!utils.isEmpty(criteria.getSelfId())) {
                filterQuery += " AND t.id = :selfId ";
                params.put("selfId", criteria.getSelfId());
            }
            if (!utils.isEmpty(criteria.getFullName())) {
                filterQuery += " AND (t.firstName LIKE :fullName OR t.lastName LIKE :fullName OR t.middleName LIKE :fullName) ";
                params.put("fullName", "%" + criteria.getFullName() + "%");
            }
            if (!utils.isEmpty(criteria.getUsername())) {
                filterQuery += " AND t.username = :username ";
                params.put("username", criteria.getUsername());
            }
            if (!utils.isEmpty(criteria.getEmail())) {
                filterQuery += " AND t.email = :email ";
                params.put("email", criteria.getEmail());
            }
            if (!utils.isEmpty(criteria.getPhone())) {
                filterQuery += " AND t.phone = :phone ";
                params.put("phone", criteria.getPhone());
            }

            return this;
        }
    }
}
