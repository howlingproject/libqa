package com.libqa.web.service;

import com.libqa.domain.Wiki;

/**
 * Created by songanji on 2015. 3. 1..
 */
public interface WikiService {
    Wiki save(Wiki wiki);

    Wiki findById(Integer wikiId);
}
