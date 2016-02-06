package com.libqa.web.view.space;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author : yion
 * @Date : 2016. 2. 6.
 * @Description :
 */

@Getter
@Setter
@ToString
public class WikiTreeNode {
    private String text;
    private String href;
    private Integer tags;
}
