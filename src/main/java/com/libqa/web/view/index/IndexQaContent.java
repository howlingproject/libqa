package com.libqa.web.view.index;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(staticName = "of")
public class IndexQaContent {
    private String title;
    private String contents;
    private String userImage;
    private String userNick;
    private String insertDate;
    private List<IndexKeyword> keywords;
    private Integer countOfReply;
}
