package com.libqa.web.view.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(staticName = "of")
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
