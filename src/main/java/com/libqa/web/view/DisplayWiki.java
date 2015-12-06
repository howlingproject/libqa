package com.libqa.web.view;

import com.libqa.web.domain.User;
import com.libqa.web.domain.Wiki;
import lombok.Data;

/**
 * Created by songanji on 2015. 11. 22..
 */
@Data
public class DisplayWiki {
    private Wiki wiki;
    private User user;

    public DisplayWiki (Wiki wiki){
        this.wiki = wiki;
    }
    public DisplayWiki(Wiki wiki, User user){
        this.wiki = wiki;
        this.user = user;
    }
}
