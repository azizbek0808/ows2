package uz.com.hibernate.dao.impl.organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.criteria.organization.OrganizationMfoCriteria;
import uz.com.dto.organization.OrganizationMfoUpdateDto;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.organization.OrganizationDao;
import uz.com.hibernate.dao.organization.OrganizationMfoDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationMfo;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.stream.Stream;

@Repository("organizationMfoDao")
public class OrganizationMfoDaoImpl extends DaoImpl<OrganizationMfo, OrganizationMfoCriteria> implements OrganizationMfoDao {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    public Stream<OrganizationMfo> list(OrganizationMfoCriteria criteria) {
        OrganizationMfoDaoImpl.CustomFilter customFilter = new OrganizationMfoDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM OrganizationMfo t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(OrganizationMfoCriteria criteria) {
        OrganizationMfoDaoImpl.CustomFilter customFilter = new OrganizationMfoDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM OrganizationMfo t WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public OrganizationMfo findByMfo(String mfo) {
        return (OrganizationMfo) findSingle("select t from OrganizationMfo t " +
                " where lower(t.mfo) = :mfo and t.state < 2 order by t.id asc ", preparing(new Entry("mfo", mfo.toLowerCase())));
    }

    @Override
    public OrganizationMfo findByMfoNotId(OrganizationMfoUpdateDto dto) {
        return (OrganizationMfo) findSingle("select t from OrganizationMfo t " +
                " where lower(t.mfo) = :mfo and t.id != :id and t.state < 2 order by t.id asc ", preparing(new Entry("mfo", dto.getMfo().toLowerCase()), new Entry("id", dto.getId())));
    }

    private class CustomFilter {
        private final OrganizationMfoCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(OrganizationMfoCriteria criteria) {
            this.criteria = criteria;
        }

        public String getFilterQuery() {
            return filterQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public OrganizationMfoDaoImpl.CustomFilter invoke() {
            filterQuery = "";
            params = preparing();

            if (!utils.isEmpty(criteria.getSelfId())) {
                filterQuery += " AND t.id = :selfId ";
                params.put("selfId", criteria.getSelfId());
            }
            if (!utils.isEmpty(criteria.getMfo())) {
                filterQuery += " AND t.mfo = :mfo ";
                params.put("mfo", criteria.getMfo());
            }

            return this;
        }
    }

}
