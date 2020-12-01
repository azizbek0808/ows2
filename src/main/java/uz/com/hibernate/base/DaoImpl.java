package uz.com.hibernate.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.springframework.beans.factory.annotation.Autowired;
import uz.com.criteria.GenericCriteria;
import uz.com.enums.State;
import uz.com.utils.BaseUtils;
import uz.com.utils.UserSession;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Stream;

public class DaoImpl<T extends _Entity, C extends GenericCriteria> implements Dao<T, C> {

    /**
     * Common logger for use in subclasses.
     */
    private static final Logger log = LogManager.getLogger(DaoImpl.class);
    @Autowired
    protected UserSession userSession;
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected BaseUtils utils;
    private final Class<T> persistentClass;

    public DaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) {
        if (entity == null) return null;
        List<Field> fields = _Entity.getFields(persistentClass);
        Optional<Field> optional = fields.stream().filter(x -> AuditInfo.class.equals(x.getType())).findFirst();
        if (optional.isPresent()) {
            Field field = optional.get();
            field.setAccessible(true);
            try {
                AuditInfo auditInfo = (AuditInfo) field.get(entity);
                if (auditInfo == null) {
                    auditInfo = new AuditInfo();
                    field.set(entity, auditInfo);
                }
                Long userId = userSession.getUser() == null ? -1 : userSession.getUser().getId();
                if (auditInfo.getCreatedBy() == null) {
                    auditInfo.setCreatedBy(userId);
                    auditInfo.setCreatedDate(new Date());
                } else {
                    auditInfo.setUpdatedBy(userId);
                    auditInfo.setUpdatedDate(new Date());
                }
            } catch (IllegalAccessException e) {
                log.error(e);
                e.printStackTrace();
            }
        }
        if (entity.isNew() || entity.getState() == null)
            entity.setState(State.NEW);
        else if (entity.getState().ordinal() < State.UPDATED.ordinal())
            entity.setState(State.UPDATED);

        Session session = getSession();
        if (session.getTransaction() == null || !session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        session.saveOrUpdate(entity);
        session.getTransaction().commit();

        return entity;
    }

    @Override
    public T get(Long id) {
        if (id == null) return null;
        return getSession().get(persistentClass, id);
    }

    @Override
    public T find(C criteria) {
        return null;
    }

    @Override
    public PageStream<T> search(C criteria) {
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.and("-state", "Deleted").and("*:*");
        org.apache.lucene.search.Query luceneQuery = queryParser("search", searchFilter.toString());
        FullTextQuery fullTextQuery = fullTextSession().createFullTextQuery(luceneQuery, persistentClass);
        fullTextQuery.initializeObjectsWith(
                ObjectLookupMethod.SECOND_LEVEL_CACHE,
                DatabaseRetrievalMethod.QUERY
        );
        if ((criteria.getPage() == null || criteria.getPerPage() == null) || (criteria.getPage() < 0 || criteria.getPerPage() <= 0)) {
            fullTextQuery.setFirstResult(0);
            fullTextQuery.setMaxResults(10);
        } else {
            fullTextQuery.setFirstResult(criteria.getPage() * criteria.getPerPage());
            fullTextQuery.setMaxResults(criteria.getPerPage());
        }
        return new PageStream<T>(fullTextQuery.stream(), Long.parseLong(String.valueOf(fullTextQuery.getResultSize())));
    }

    @Override
    public Stream<T> list(C criteria) {
        String query = "SELECT t FROM " + persistentClass.getSimpleName() + " t WHERE t.state <> 2 ";
        if (persistentClass.isInstance(Item.class)) {
            query += " ORDER BY t.name ";
        } else {
            query += " ORDER BY t.id desc";
        }
        return getSession().createQuery(query).stream();
    }

    @Override
    public Long total(C criteria) {
        String query = "SELECT count(t) FROM " + persistentClass.getSimpleName() + " t WHERE t.state <> 2 ";
        return (Long) getSession().createQuery(query).getResultList().get(0);
    }

    @Override
    public Stream find(String query, Map<String, ?> params) {
        Query queryObject = getSession().createQuery(query);
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return queryObject.stream();
    }

    @Override
    public Stream findInterval(String query, Map<String, ?> params, Integer page, Integer perPage) {
        Query queryObject = getSession().createQuery(query);
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return getResults(queryObject, page, perPage);
    }

    @Override
    public Stream findNativeInterval(String query, Map<String, ?> params, Integer page, Integer perPage) {
        Query queryObject = getSession().createNativeQuery(query);
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return getResults(queryObject, page, perPage);
    }

    @Override
    public Object findSingle(String query, Map<String, ?> params) {
        Query queryObject = getSession().createQuery(query);
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }

        queryObject.setMaxResults(1);
        List list = queryObject.getResultList();
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Object findSingleNative(String query, Map<String, ?> params) {
        Query queryObject = getSession().createNativeQuery(query);
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }

        queryObject.setMaxResults(1);
        List list = queryObject.getResultList();
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public void delete(T entity) {
        if (entity == null) return;
        List<Field> fields = _Entity.getFields(persistentClass);
        Optional<Field> optional = fields.stream().filter(x -> AuditInfo.class.equals(x.getType())).findFirst();
        if (optional.isPresent()) {
            Field field = optional.get();
            field.setAccessible(true);
            try {
                AuditInfo auditInfo = (AuditInfo) field.get(entity);
                if (auditInfo == null) {
                    auditInfo = new AuditInfo();
                    field.set(entity, auditInfo);
                }
                auditInfo.setUpdatedBy(userSession.getUser() == null ? -1 : userSession.getUser().getId());
                auditInfo.setUpdatedDate(new Date());

            } catch (IllegalAccessException e) {
                log.error(e);
                e.printStackTrace();
            }
        }

        entity.setState(State.DELETED);

        Session session = getSession();
        if (session.getTransaction() == null || !session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        T obj = get(id);
        if (obj != null)
            getSession().delete(obj);
    }

    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    protected StringBuilder onSortBy(C criteria) {
        StringBuilder sorting = new StringBuilder();
        if (!utils.isEmpty(criteria.getSortBy())) {
            String ascDesc = criteria.getSortDirection();
            sorting.append(" ORDER BY ").append("t.").append(criteria.getSortBy()).append(" ").append(ascDesc);
        }
        return sorting;
    }

    protected Stream getResults(Query query, Integer page, Integer perPage) {
        if ((page == null || perPage == null) || (page < 0 || perPage <= 0)) {
            return query.stream();
        } else {
            return query.setFirstResult(page * perPage).setMaxResults(perPage).stream();
        }
    }

    protected Map<String, Object> preparing(Entry... params) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> item : params) {
            if (item == null) continue;
            map.put(item.getKey(), item.getValue());
        }
        return map;
    }

    protected static class Entry implements Map.Entry {
        private String key;
        private Object value;

        public Entry(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            return this.value = value;
        }
    }

    protected FullTextSession fullTextSession() {
        FullTextSession fullTextSession = Search.getFullTextSession(getSession());
        return fullTextSession;
    }

    protected SearchFactory searchFactory() {
        SearchFactory searchFactory = fullTextSession().getSearchFactory();
        return searchFactory;
    }

    protected QueryParser queryParser(String field) {
        QueryParser parser = new QueryParser(field, searchFactory().getAnalyzer(persistentClass));
        parser.setAllowLeadingWildcard(true);
        parser.setLowercaseExpandedTerms(true);
        return parser;
    }

    protected org.apache.lucene.search.Query queryParser(String field, String query) {
        QueryParser parser = new QueryParser(field, searchFactory().getAnalyzer(persistentClass));
//        parser.setAllowLeadingWildcard(true);
        parser.setLowercaseExpandedTerms(true);
        try {
            return parser.parse(query);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class SearchFilter {
        private StringBuilder query = new StringBuilder();

        public SearchFilter and(String condition) {
            if (query.length() > 0) {
                query.append(" AND ");
            }
            query.append(condition);
            return this;
        }

        public SearchFilter and(String field, String value) {
            value = value.trim();
            if (query.length() > 0) {
                query.append(" AND ");
            }
            if (value.contains("*") && !value.startsWith("-"))
                query.append(field).append(":").append(value);
            else
                query.append(field).append(":\"").append(value).append("\"");
            return this;
        }

        public SearchFilter or(String condition) {
            if (query.length() > 0) {
                query.append(" OR ");
            }
            query.append(condition);
            return this;
        }

        public SearchFilter or(String field, String value) {
            value = value.trim();
            if (query.length() > 0) {
                query.append(" OR ");
            }
            value = String.format("(%s)", value);
            if (value.contains("*") && !value.startsWith("-"))
                query.append(field).append(":").append(value);
            else
                query.append(field).append(":\"").append(value).append("\"");
            return this;
        }

        public StringBuilder getQuery() {
            return query;
        }

        @Override
        public String toString() {
            return getQuery().toString();
        }
    }
}
