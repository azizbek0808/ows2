package uz.com.hibernate.dao.impl.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.settings.BusinessDirectionCriteria;
import uz.com.criteria.settings.TypeCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.settings.BusinessDirectionDao;
import uz.com.hibernate.dao.settings.TypeDao;
import uz.com.hibernate.domain.settings.BusinessDirection;
import uz.com.hibernate.domain.settings.Type;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.stream.Stream;

@Repository("businessDirectionDao")
public class BusinessDirectionDaoImpl extends DaoImpl<BusinessDirection, BusinessDirectionCriteria> implements BusinessDirectionDao {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    public Stream<BusinessDirection> list(BusinessDirectionCriteria criteria) {
        BusinessDirectionDaoImpl.CustomFilter customFilter = new BusinessDirectionDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM BusinessDirection t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(BusinessDirectionCriteria criteria) {
        BusinessDirectionDaoImpl.CustomFilter customFilter = new BusinessDirectionDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM BusinessDirection t WHERE t.state <> 2 " + filterQuery, params);
    }

    private class CustomFilter {
        private final BusinessDirectionCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(BusinessDirectionCriteria criteria) {
            this.criteria = criteria;
        }

        public String getFilterQuery() {
            return filterQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public BusinessDirectionDaoImpl.CustomFilter invoke() {
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
            if (!utils.isEmpty(criteria.getCodeName())) {
                filterQuery += " AND t.codeName = :codeName ";
                params.put("codeName", criteria.getCodeName());
            }

            return this;
        }
    }

}
