package uz.com.hibernate.dao.impl.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.settings.TypeCriteria;
import uz.com.dto.settings.TypeUpdateDto;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.settings.TypeDao;
import uz.com.hibernate.domain.auth.Role;
import uz.com.hibernate.domain.settings.Type;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.stream.Stream;

@Repository("typeDao")
public class TypeDaoImpl extends DaoImpl<Type, TypeCriteria> implements TypeDao {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    public Stream<Type> list(TypeCriteria criteria) {
        TypeDaoImpl.CustomFilter customFilter = new TypeDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT t FROM Type t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(TypeCriteria criteria) {
        TypeDaoImpl.CustomFilter customFilter = new TypeDaoImpl.CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM Type t WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public Type findByValue(String value) {
        return (Type) findSingle("select t from Type t " +
                " where lower(t.value) = :value and t.state < 2 order by t.id asc ", preparing(new Entry("value", value.toLowerCase())));
    }

    @Override
    public Type findByValueNotId(TypeUpdateDto dto) {
        return (Type) findSingle("select t from Type t " +
                " where lower(t.value) = :value and t.id != :id and t.state < 2 order by t.id asc ", preparing(new Entry("value", dto.getValue().toLowerCase()),new Entry("id", dto.getId())));
    }

    private class CustomFilter {
        private final TypeCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(TypeCriteria criteria) {
            this.criteria = criteria;
        }

        public String getFilterQuery() {
            return filterQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public TypeDaoImpl.CustomFilter invoke() {
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
            if (!utils.isEmpty(criteria.getTypeCode())) {
                filterQuery += " AND t.typeCode = :typeCode ";
                params.put("typeCode", criteria.getTypeCode());
            }
            if (!utils.isEmpty(criteria.getValue())) {
                filterQuery += " AND t.value = :value ";
                params.put("value", criteria.getValue());
            }

            return this;
        }
    }

}
