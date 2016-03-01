package com.libqa.web.view.space;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yion
 * @Date : 2016. 3. 1.
 * @Description :
 */
@Getter
@Setter
public class TreeModel {

    private Integer id;
    private String text;
    private Integer parentId;
    private String href;
    private int tags;

    private List<TreeModel> nodes = new ArrayList<>();
}
