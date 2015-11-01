package com.libqa.web.view;

import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.User;
import com.libqa.web.domain.Wiki;
import lombok.Getter;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 8. 23.
 * @Description :
 */

@Getter
public class SpaceWikiList {

    private Wiki wiki;
    private User user;
    private List<Keyword> keywords;
    private int replyCount;

    public SpaceWikiList(Wiki wiki, User user, List keywords, int replyCount) {
        this.wiki = wiki;
        this.user = user;
        this.keywords = keywords;
        this.replyCount = replyCount;
    }
}
