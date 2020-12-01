package uz.com.hibernate.base;

import org.hibernate.Session;
import uz.com.criteria.GenericCriteria;

import java.util.Map;
import java.util.stream.Stream;

public interface Dao<T extends _Entity, C extends GenericCriteria> {

    T save(T entity);

    T get(Long id);

    T find(C criteria);

    PageStream<T> search(C criteria);

    Stream<T> list(C criteria);

    Long total(C criteria);

    Stream find(String query, Map<String, ?> params);

    Stream findInterval(String query, Map<String, ?> params, Integer page, Integer perPage);

    Stream findNativeInterval(String query, Map<String, ?> params, Integer page, Integer perPage);

    Object findSingle(String query, Map<String, ?> params);

    Object findSingleNative(String query, Map<String, ?> params);

    void delete(T entity);

    void delete(Long id);

    Session getSession();
}
