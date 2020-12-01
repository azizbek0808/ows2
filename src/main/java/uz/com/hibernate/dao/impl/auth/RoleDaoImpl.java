package uz.com.hibernate.dao.impl.auth;

import org.springframework.stereotype.Repository;
import uz.com.criteria.auth.RoleCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.auth.RoleDao;
import uz.com.hibernate.domain.auth.Permission;
import uz.com.hibernate.domain.auth.Role;

import java.util.Map;
import java.util.stream.Stream;

@Repository("roleDao")
public class RoleDaoImpl extends DaoImpl<Role, RoleCriteria> implements RoleDao {

    @Override
    public Stream<Role> list(RoleCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM Role t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(RoleCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM Role t WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public Role findByCode(String code) {
        return (Role) findSingle("select t from Role t " +
                " where lower(t.code) = :code and t.state < 2 order by t.id asc ", preparing(new Entry("code", code.toLowerCase())));
    }

    private class CustomFilter {
        private final RoleCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(RoleCriteria criteria) {
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
            if (!utils.isEmpty(criteria.getCode())) {
                filterQuery += " AND lower(t.code) like :code ";
                params.put("code","%" + criteria.getCode().toLowerCase() + "%");
            }
            if (!utils.isEmpty(criteria.getName())) {
                filterQuery += " AND lower(t.name) like :name ";
                params.put("name","%" + criteria.getName().toLowerCase() + "%");
            }

            return this;
        }
    }
}
