package uz.com.hibernate.dao.impl.organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.organization.OrganizationAddressCriteria;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.organization.OrganizationAddressUpdateDto;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.organization.OrganizationAddressDao;
import uz.com.hibernate.dao.organization.OrganizationDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationAddress;
import uz.com.hibernate.domain.organization.OrganizationContact;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.stream.Stream;

@Repository("organizationAddressDao")
public class OrganizationAddressDaoImpl extends DaoImpl<OrganizationAddress, OrganizationAddressCriteria> implements OrganizationAddressDao {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    public Stream<OrganizationAddress> list(OrganizationAddressCriteria criteria) {
        OrganizationAddressDaoImpl.CustomFilter customFilter = new OrganizationAddressDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM OrganizationAddress t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(OrganizationAddressCriteria criteria) {
        OrganizationAddressDaoImpl.CustomFilter customFilter = new OrganizationAddressDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM OrganizationAddress t WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public OrganizationAddress findByName(String name) {
        return (OrganizationAddress) findSingle("select t from OrganizationAddress t " +
                " where lower(t.name) = :name and t.state < 2 order by t.id asc ", preparing(new Entry("name", name.toLowerCase())));
    }

    @Override
    public OrganizationAddress findByNameNotId(OrganizationAddressUpdateDto dto) {
        return (OrganizationAddress) findSingle("select t from OrganizationAddress t " +
                " where lower(t.name) = :name and t.id != :id and t.state < 2 order by t.id asc ", preparing(new Entry("name", dto.getName().toLowerCase()), new Entry("id", dto.getId())));
    }

    private class CustomFilter {
        private final OrganizationAddressCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(OrganizationAddressCriteria criteria) {
            this.criteria = criteria;
        }

        public String getFilterQuery() {
            return filterQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public OrganizationAddressDaoImpl.CustomFilter invoke() {
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

            return this;
        }
    }

}
