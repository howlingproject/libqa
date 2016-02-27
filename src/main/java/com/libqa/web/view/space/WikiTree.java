package com.libqa.web.view.space;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2016. 2. 6.
 * @Description : 위키 목록을 트리로 보여준다.
 */

@Getter
@Setter
@ToString
public class WikiTree {

    private int groupIdx;
    private int wikiId;
    private int parentsId;
    private int depthIdx;
    private int orderIdx;
    private String title;
    private int maxRows;
    private boolean hasChild;   // 자식이 있는가?
}
