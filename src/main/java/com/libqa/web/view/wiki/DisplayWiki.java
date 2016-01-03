package com.libqa.web.view.wiki;

import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.User;
import com.libqa.web.domain.Wiki;
import lombok.Data;

import java.util.List;

/**
 * Created by songanji on 2015. 11. 22..
 */
@Data
public class DisplayWiki {
    private Wiki wiki;
    private User user;
    private List<Keyword> keywords;


    public DisplayWiki (Wiki wiki){
        this.wiki = wiki;
    }
    public DisplayWiki(Wiki wiki, User user){
        this.wiki = wiki;
        this.user = user;
    }
    public DisplayWiki(Wiki wiki, User user, List<Keyword> keywords){
        this.wiki = wiki;
        this.user = user;
        this.keywords = keywords;
    }
}
