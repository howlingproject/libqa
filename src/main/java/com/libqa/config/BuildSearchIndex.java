package com.libqa.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Configuration
public class BuildSearchIndex {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void initialize() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            log.error("An error occurred trying to build the search index: " + e.toString());
        }
    }
}