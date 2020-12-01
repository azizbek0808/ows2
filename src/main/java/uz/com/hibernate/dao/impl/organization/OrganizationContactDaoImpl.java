package uz.com.hibernate.dao.impl.organization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.organization.OrganizationContactCriteria;
import uz.com.criteria.organization.OrganizationCriteria;
import uz.com.dto.organization.OrganizationContactUpdateDto;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.organization.OrganizationContactDao;
import uz.com.hibernate.dao.organization.OrganizationDao;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationContact;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.stream.Stream;

@Repository("organizationContactDao")
public class OrganizationContactDaoImpl extends DaoImpl<OrganizationContact, OrganizationContactCriteria> implements OrganizationContactDao {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    public Stream<OrganizationContact> list(OrganizationContactCriteria criteria) {
        OrganizationContactDaoImpl.CustomFilter customFilter = new OrganizationContactDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM OrganizationContact t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(OrganizationContactCriteria criteria) {
        OrganizationContactDaoImpl.CustomFilter customFilter = new OrganizationContactDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM OrganizationContact t WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public OrganizationContact findByEmail(String email) {
        return (OrganizationContact) findSingle("select t from OrganizationContact t " +
                " where t.email = :email and t.state < 2 order by t.id asc ", preparing(new Entry("email", email)));
    }

    @Override
    public OrganizationContact findByEmailNotId(OrganizationContactUpdateDto dto) {
        return (OrganizationContact) findSingle("select t from OrganizationContact t " +
                " where t.email = :email and t.id != :id and t.state < 2 order by t.id asc ", preparing(new Entry("email", dto.getEmail()), new Entry("id", dto.getId())));
    }



    private class CustomFilter {
        private final OrganizationContactCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(OrganizationContactCriteria criteria) {
            this.criteria = criteria;
        }

        public String getFilterQuery() {
            return filterQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public OrganizationContactDaoImpl.CustomFilter invoke() {
            filterQuery = "";
            params = preparing();

            if (!utils.isEmpty(criteria.getSelfId())) {
                filterQuery += " AND t.id = :selfId ";
                params.put("selfId", criteria.getSelfId());
            }
            if (!utils.isEmpty(criteria.getEmail())) {
                filterQuery += " AND t.email = :email ";
                params.put("email", criteria.getEmail());
            }
            if (!utils.isEmpty(criteria.getPhoneNumber())) {
                filterQuery += " AND t.phoneNumber = :phoneNumber ";
                params.put("inn", criteria.getPhoneNumber());
            }
            return this;
        }
    }

}
