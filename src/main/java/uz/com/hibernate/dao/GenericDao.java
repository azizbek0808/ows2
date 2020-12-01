package uz.com.hibernate.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import uz.com.criteria.GenericCriteria;
import uz.com.exception.CustomSqlException;
import uz.com.hibernate.base._Entity;
import uz.com.utils.BaseUtils;
import uz.com.utils.UserSession;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.List;


public abstract class GenericDao<T extends _Entity, C extends GenericCriteria> {

    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
    @Autowired
    protected EntityManager entityManager;
    /*@Autowired
    protected BaseObjectMapper baseObjectMapper;*/
    @Autowired
    protected BaseUtils utils;
    @Autowired
    protected UserSession userSession;
    protected SimpleDateFormat dateTimeFormat;
    protected Gson gson;
    protected JpaEntityInformation<T, ?> entityInformation;
    private Class<T> persistentClass;
    /*private Boolean isAdmin;*/

    @SuppressWarnings("unchecked")
    public GenericDao() {
        this.dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        initEntityInformation();
    }

    /*protected static void addLanguageWhereCause(Map<String, Object> params, Map<Headers, String> headers, List<String> whereCause, String alias) {
        if (headers.containsKey(Headers.LANGUAGE)) {
            whereCause.add(alias + ".language.code =:" + Headers.LANGUAGE.key);
            params.put(Headers.LANGUAGE.key, headers.get(Headers.LANGUAGE));
        } else {
            whereCause.add(alias + ".language.code =:" + Headers.LANGUAGE.key);
            params.put(Headers.LANGUAGE.key, Headers.LANGUAGE.defValue);
        }
    }*/

    private void initEntityInformation() {
        if (entityManager != null && entityInformation == null) {
            this.entityInformation = JpaEntityInformationSupport.getEntityInformation(persistentClass, entityManager);
        }
    }

    /*public T find(Long id) {
        try {
            return entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t where t.deleted = 0 and t.id = " + id, persistentClass).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public <G> G find(Long id, Class<G> object) {
        try {
            return entityManager.createQuery("Select t from " + object.getSimpleName() + " t where t.deleted = 0 and t.id = " + id, object).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    protected Optional<T> findById(Long id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        return Optional.ofNullable(find(id));
    }

    public T find(C criteria) {
        Query query = findInit(criteria, false);

        try {
            return (T) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
//        throw new RuntimeException("No result found");
    }*/

  /*  public <G> G findGeneric(C criteria) {
        Query query = findInit(criteria, false);

        try {
            return (G) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
//        throw new RuntimeException("No result found");
    }*/

    /*public List<T> findAll(C criteria) {
        return findAllGeneric(criteria);
    }

    public <G> List<G> findAllSelections (C criteria) {
        return findAllGeneric(criteria);
    }*/

    /*protected <G> List<G> findAllGeneric(C criteria) {
        Query query = findInit(criteria, false);
        return getResults(criteria, query);
    }

    public Long getTotalCount(C criteria) {
        Query query = findInit(criteria, true);
        return (Long) query.getSingleResult();
    }*/

    /*private Query findInit(C criteria, boolean onDefineCount) {
        Query query;
        Map<String, Object> params = new HashMap<>();
        List<String> whereCause = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();

        defineCriteriaOnQuerying(criteria, whereCause, params, queryBuilder);

        query = defineQuerySelect(criteria, queryBuilder, onDefineCount);

        defineSetterParams(query, params);

        return query;
    }*/

    /*private void defineSetterParams(Query query, Map<String, Object> params) {
        params.keySet().forEach(t -> query.setParameter(t, params.get(t)));
    }

    protected void defineCriteriaOnQuerying(C criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {
        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }*/

    /*protected void onDefineWhereCause(C criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {
        if (!whereCause.isEmpty()) {
            queryBuilder.append(" and ").append(StringUtils.join(whereCause, " and "));
        }
    }*/

    /*protected Query defineQuerySelect(C criteria, StringBuilder queryBuilder, boolean onDefineCount) {
        if (criteria.isSelection()) {
            String queryStr = " select distinct new  uz.genesis.trello.dto.SelectDto(t.id," + (utils.isEmpty(criteria.getSelectionFieldName()) ? " t.name " : "t." +criteria.getSelectionFieldName()) + " )  from " + persistentClass.getSimpleName() + " t " +
                    joinStringOnQuerying(criteria) +
                    " where t.deleted = 0 " + queryBuilder.toString() + onSortBy(criteria).toString();
            return entityManager.createQuery(queryStr);
        }
        String queryStr = " select " + (onDefineCount ? " count(t) " : " t ") + " from " + persistentClass.getSimpleName() + " t " +
                joinStringOnQuerying(criteria) +
                " where t.deleted = 0 " + queryBuilder.toString() + (onDefineCount ? "" : onSortBy(criteria).toString());
        return onDefineCount ? entityManager.createQuery(queryStr, Long.class) : entityManager.createQuery(queryStr);
    }*/

    /*protected StringBuilder joinStringOnQuerying(C criteria) {
        return new StringBuilder();
    }*/

    /*protected Object[] getCustomDto(C criteria) {
        return findGeneric(criteria);
    }*/

    /*protected List<Object[]> getAllCustomDtoList(C criteria) {
        return findAllGeneric(criteria);
    }*/

    /*protected StringBuilder onSortBy(C criteria) {
        StringBuilder sorting = new StringBuilder();
        if (!utils.isEmpty(criteria.getSortBy())) {
            String ascDesc = criteria.getSortDirection();
            sorting.append(" order by ").append("t.").append(criteria.getSortBy()).append(" ").append(ascDesc);
        }
        return sorting;
    }*/

    /*protected <G> List<G> getResults(C criteria, Query query) {
        if ((criteria.getPage() == null || criteria.getPerPage() == null) || (criteria.getPage() < 0 || criteria.getPerPage() <= 0)) {
            return query.getResultList();
        } else {
            return query.setFirstResult(criteria.getPage() * criteria.getPerPage())
                    .setMaxResults(criteria.getPerPage()).getResultList();
        }
    }*/

    /*@Transactional
    public <S extends T> S save(S entity) {
       *//* initEntityInformation();
        if (entityInformation.isNew(entity)) {
            entity.setCreatedBy(userSession.getUser().getId());
            entityManager.persist(entity);
            return entity;
        } else {
            entity.setUpdatedBy(userSession.getUser().getId());
            return entityManager.merge(entity);
        }*//*

        return null;
    }*/

    /*@Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {

        Assert.notNull(entities, "The given Iterable of entities not be null!");

        List<S> result = new ArrayList<S>();

        for (S entity : entities) {
            result.add(save(entity));
        }

        return result;
    }*/

    public <C> Long create(C domain, String methodName) {
        Session session = entityManager.unwrap(Session.class);
        return (Long) call(domain, methodName, session, Types.BIGINT);
    }

    /*public <C> Boolean update(C domain, String methodName) {
        Session session = entityManager.unwrap(Session.class);
        return (Boolean) call(domain, methodName, session, Types.BOOLEAN);
    }*/

    public <R> R call(T domain, String methodName, int outParamType) {
        Session session = entityManager.unwrap(Session.class);

        return (R) call(domain, methodName, session, outParamType);
    }

    public <C, R> R call(C domain, String methodName, int outParamType) {
        Session session = entityManager.unwrap(Session.class);

        return (R) call(domain, methodName, session, outParamType);
    }

    public <R> R call(List<FunctionParam> params, String methodName, int outParamType) {
        Session session = entityManager.unwrap(Session.class);
        return (R) call(params, methodName, session, outParamType);
    }


    private <C> Object call(C domain, String methodName, Session session, int outParamType) {
        return session.doReturningWork(
                connection -> {
                    try (CallableStatement function = connection
                            .prepareCall(
                                    "{ ? = call " + methodName + " (?, ?) }")) {
                        function.registerOutParameter(1, outParamType);
                        function.setString(2, gson.toJson(domain));
                        prepareFunction(function);
                        return returnByOutType(outParamType, function);
                    } catch (Exception ex) {
                        throw new CustomSqlException(ex.getMessage(), ex.getCause());
                    }
                });
    }

    private Serializable call(List<FunctionParam> params, String methodName, Session session, int outParamType) {
        return session.doReturningWork(
                connection -> {
                    try (CallableStatement function = connection
                            .prepareCall(
                                    "{ ? = call " + methodName + utils.generateParamText(params) + " }")) {
                        function.registerOutParameter(1, outParamType);

                        for (int i = 2; i < params.size() + 2; i++) {
                            FunctionParam param = params.get(i - 2);
                            function.setObject(i, param.getParam(), param.getParamType());
                        }

                        function.execute();

                        if (!utils.isEmpty(function.getWarnings())) {
                            throw new RuntimeException(function.getWarnings().getMessage());
                        }

                        return returnByOutType(outParamType, function);
                    } catch (Exception ex) {
                        throw new CustomSqlException(ex.getMessage(), ex.getCause());
                    }
                });
    }

    private Serializable returnByOutType(int outParamType, CallableStatement function) throws SQLException {
        switch (outParamType) {
            case Types.BOOLEAN:
                return function.getBoolean(1);
            case Types.VARCHAR:
                return function.getString(1);
            case Types.BIGINT:
                return function.getLong(1);
            case Types.INTEGER:
                return function.getInt(1);
            case Types.NUMERIC:
                return function.getDouble(1);
        }
        return function.getLong(1);
    }

    /*public boolean delete(Long id, String methodName) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(
                connection -> {
                    try (CallableStatement function = connection
                            .prepareCall(
                                    "{ ? = call " + methodName + " (?, ?) }")) {
                        function.registerOutParameter(1, Types.BOOLEAN);
                        function.setLong(2, id);
                        prepareFunction(function);
                        return function.getBoolean(1);
                    } catch (Exception ex) {
                        throw new CustomSqlException(ex.getMessage(), ex.getCause());
                    }
                });
    }*/

    private void prepareFunction(CallableStatement function) throws SQLException {
        function.setLong(3, userSession.getUser() == null ? -1 : userSession.getUser().getId());
        function.execute();

        if (!utils.isEmpty(function.getWarnings())) {
            throw new RuntimeException(function.getWarnings().getMessage());
        }
    }


    /*protected boolean isAdmin() {
        return hasRole("ADMIN", userSession.getUserName());
    }*/

    /*protected void addOrganizationCheck(StringBuilder queryBuilder, Map<String, Object> params, String aliesName) {
        if (!isAdmin()) {
            queryBuilder
                    .append(" and ")
                    .append(aliesName).append(".organizationId")
                    .append(" in (select o.id from Organization o where o.id = :organizationId) ");

            User currentUser = userSession.getUser();
            params.put("organizationId", currentUser.getOrganizationId());
        }
    }*/


    /*protected boolean hasRole(String role) {
        return hasRole(role, userSession.getUserName());
    }*/

    /*protected boolean hasRole(String role, String userName) {
        Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(
                connection -> {
                    try (CallableStatement function = connection
                            .prepareCall(
                                    "{ ? = call hasrole (?, ?) }")) {
                        function.registerOutParameter(1, Types.BOOLEAN);
                        function.setString(2, role);
                        function.setString(3, userName);
                        function.execute();

                        if (!utils.isEmpty(function.getWarnings())) {
                            throw new RuntimeException(function.getWarnings().getMessage());
                        }

                        return function.getBoolean(1);
                    } catch (Exception ex) {
                        throw new CustomSqlException(ex.getMessage(), ex.getCause());
                    }
                });
    }*/

    /*@Transactional
    public void delete(T entity) {

        *//*Assert.notNull(entity, "The entity must not be null!");
        entity.setUpdatedBy(userSession.getUser().getId());
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));*//*
    }

    @Transactional
    public void deleteById(Long id) {
        *//*initEntityInformation();
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);

        delete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1)));*//*
    }

    @Transactional
    public void deleteAll(Iterable<? extends T> entities) {

        Assert.notNull(entities, "The given Iterable of entities not be null!");

        for (T entity : entities) {
            delete(entity);
        }
    }*/
}
