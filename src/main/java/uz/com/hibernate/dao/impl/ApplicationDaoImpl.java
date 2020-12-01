package uz.com.hibernate.dao.impl;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.springframework.stereotype.Repository;
import uz.com.criteria.main.ApplicationCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.base.PageStream;
import uz.com.hibernate.dao.ApplicationDao;
import uz.com.hibernate.domain.application.Application;

@Repository("applicationDao")
public class ApplicationDaoImpl extends DaoImpl<Application, ApplicationCriteria> implements ApplicationDao {

    @Override
    public PageStream<Application> search(ApplicationCriteria criteria) {
        CustomSearchFilter customSearchFilter = new CustomSearchFilter(criteria).invoke();
        org.apache.lucene.search.Query luceneQuery = queryParser("search", customSearchFilter.filterQuery);
        FullTextQuery fullTextQuery = fullTextSession().createFullTextQuery(luceneQuery, Application.class);
        fullTextQuery.initializeObjectsWith(
                ObjectLookupMethod.SECOND_LEVEL_CACHE,
                DatabaseRetrievalMethod.QUERY
        );
        fullTextQuery.setCacheable(true);
        fullTextQuery.setSort(new Sort(new SortField("id", SortField.Type.STRING, true)));
        if ((criteria.getPage() == null || criteria.getPerPage() == null) || (criteria.getPage() < 0 || criteria.getPerPage() <= 0)) {
            fullTextQuery.setFirstResult(0);
            fullTextQuery.setMaxResults(10);
        } else {
            fullTextQuery.setFirstResult(criteria.getPage() * criteria.getPerPage());
            fullTextQuery.setMaxResults(criteria.getPerPage());
        }
        return new PageStream<Application>(fullTextQuery.stream(), Long.parseLong(String.valueOf(fullTextQuery.getResultSize())));
    }

    private class CustomSearchFilter {
        private final ApplicationCriteria criteria;
        private String filterQuery;

        private CustomSearchFilter(ApplicationCriteria criteria) {
            this.criteria = criteria;
        }

        public CustomSearchFilter invoke() {
            SearchFilter searchFilter = new SearchFilter();
            if (!utils.isEmpty(criteria.getSelfId())) {
                searchFilter.and("id", "" + criteria.getSelfId());
            }
            if (!utils.isEmpty(criteria.getNumber())) {
                searchFilter.and("number", criteria.getNumber());
            }
            if (searchFilter.getQuery().length() < 1) {
                searchFilter.and("*:*");
            }
            searchFilter.and("-state", "Deleted");
            filterQuery = searchFilter.toString();
            return this;
        }
    }
}
