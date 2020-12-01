package uz.com.hibernate.dao.impl.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import uz.com.criteria.auth.PermissionCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.auth.PermissionDao;
import uz.com.hibernate.domain.auth.Permission;

import java.util.Map;
import java.util.stream.Stream;

@Repository("permissionDao")
public class PermissionDaoImpl extends DaoImpl<Permission, PermissionCriteria> implements PermissionDao {
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public Stream<Permission> list(PermissionCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM Permission t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Permission findByCode(String code) {
        return (Permission) findSingle("select t from Permission t " +
                " where lower(t.code) = :code and t.state < 2 order by t.id asc ", preparing(new Entry("code", code.toLowerCase())));
    }

    @Override
    public Long total(PermissionCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM Permission t WHERE t.state <> 2 " + filterQuery, params);
    }

    private class CustomFilter {
        private final PermissionCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(PermissionCriteria criteria) {
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
                params.put("name", "%" + criteria.getName().toLowerCase() + "%");
            }
            if (!utils.isEmpty(criteria.getParentId())) {
                filterQuery += " AND t.parentId = :parentId ";
                params.put("parentId", criteria.getParentId());
            }

            return this;
        }
    }
}
