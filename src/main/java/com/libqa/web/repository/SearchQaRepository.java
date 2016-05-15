package com.libqa.web.repository;

import com.libqa.web.domain.QaContent;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class SearchQaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<QaContent> search(String matchText) {

        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(QaContent.class).get();

        // a very basic query by keywords
        Query query = queryBuilder
                .keyword()
                .onFields("title", "contents")
                .matching(matchText)
                .createQuery();

        // wrap Lucene query in an Hibernate Query object
        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, QaContent.class);

        // execute search and return results (sorted by relevance as default)
        @SuppressWarnings("unchecked")
        List results = jpaQuery.getResultList();

        return results;
    }

}
