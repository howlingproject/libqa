package com.libqa.web.view.search;

import lombok.Getter;

import java.util.List;

@Getter
public class DisplaySearchResult {
    private Integer id;
    private String title;
    private String contents;
    private String userImage;
    private String userNick;
    private String insertDate;
    private List<String> keywords;
    private Integer countOfReply;
}
