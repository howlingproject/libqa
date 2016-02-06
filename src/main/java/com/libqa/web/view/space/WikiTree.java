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

    private String text;
    private String href;
    private Integer tags;
    private List<WikiTreeNode> nodes;

}
