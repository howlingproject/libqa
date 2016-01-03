package com.libqa.web.view.space;

import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Space;
import lombok.Getter;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 6. 21.
 * @Description :
 */
@Getter
public class SpaceMain {

    private Space space;
    private List<Keyword> keywords;
    private int wikiCount;

    public SpaceMain(Space space, int wikiCount, List keywords) {
        this.space = space;
        this.wikiCount = wikiCount;
        this.keywords = keywords;
    }

}
