package uz.com.hibernate.dao.impl.organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.criteria.settings.TypeCriteria;
import uz.com.dto.organization.OrganizationUpdateDto;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.organization.OrganizationDao;
import uz.com.hibernate.dao.settings.TypeDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.settings.Type;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.stream.Stream;

@Repository("organizationDao")
public class OrganizationDaoImpl extends DaoImpl<Organization, OrganizationCriteria> implements OrganizationDao {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    public Stream<Organization> list(OrganizationCriteria criteria) {
        OrganizationDaoImpl.CustomFilter customFilter = new OrganizationDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM Organization t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(OrganizationCriteria criteria) {
        OrganizationDaoImpl.CustomFilter customFilter = new OrganizationDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM Organization t WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public Organization findByName(String name) {
        return (Organization) findSingle("select t from Organization t " +
                " where lower(t.name) = :name and t.state < 2 order by t.id asc ", preparing(new Entry("name", name.toLowerCase())));
    }

    @Override
    public Organization findByNick(String nick) {
        return (Organization) findSingle("select t from Organization t " +
                " where lower(t.nick) = :nick and t.state < 2 order by t.id asc ", preparing(new Entry("nick", nick.toLowerCase())));
    }

    @Override
    public Organization findByNameNotId(OrganizationUpdateDto dto) {
        return (Organization) findSingle("select t from Organization t " +
                " where lower(t.name) = :name and t.id != :id and t.state < 2 order by t.id asc ", preparing(new Entry("name", dto.getName().toLowerCase()), new Entry("id", dto.getId())));
    }

    @Override
    public Organization findByNickNotId(OrganizationUpdateDto dto) {
        return (Organization) findSingle("select t from Organization t " +
                " where lower(t.nick) = :nick and t.id != :id and t.state < 2 order by t.id asc ", preparing(new Entry("nick", dto.getNick().toLowerCase()), new Entry("id", dto.getId())));
    }

    private class CustomFilter {
        private final OrganizationCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(OrganizationCriteria criteria) {
            this.criteria = criteria;
        }

        public String getFilterQuery() {
            return filterQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public OrganizationDaoImpl.CustomFilter invoke() {
            filterQuery = "";
            params = preparing();

            if (!utils.isEmpty(criteria.getSelfId())) {
                filterQuery += " AND t.id = :selfId ";
                params.put("selfId", criteria.getSelfId());
            }
            if (!utils.isEmpty(criteria.getName())) {
                filterQuery += " AND t.name = :name ";
                params.put("name", criteria.getName());
            }
            if (!utils.isEmpty(criteria.getInn())) {
                filterQuery += " AND t.inn = :inn ";
                params.put("inn", criteria.getInn());
            }

            return this;
        }
    }

}
