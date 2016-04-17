package com.libqa.web.view.space;

import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.User;
import com.libqa.web.domain.Wiki;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2016. 4. 17.
 * @Description :
 */
@Setter
@Getter
public class SpaceWiki {

    private Wiki wiki;
    private User user;
    private List<Keyword> keywords;
    private int replyCount;

    public SpaceWiki(Wiki wiki, User user, List keywords, int replyCount) {
        this.wiki = wiki;
        this.user = user;
        this.keywords = keywords;
        this.replyCount = replyCount;
    }


    public SpaceWiki() {

    }

}
