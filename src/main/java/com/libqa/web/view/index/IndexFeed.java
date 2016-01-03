package com.libqa.web.view.index;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(staticName = "of")
public class IndexFeed {
    private String userImage;
    private String content;
    private Integer countOfReply;
}
