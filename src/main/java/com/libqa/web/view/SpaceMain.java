package com.libqa.web.view;

import com.libqa.web.domain.Space;
import lombok.Getter;

/**
 * @Author : yion
 * @Date : 2015. 6. 21.
 * @Description :
 */
@Getter
public class SpaceMain {

    private Space space;
    private int wikiCount;

    public SpaceMain(Space space, int wikiCount) {
        this.space = space;
        this.wikiCount = wikiCount;
    }

}
