package com.libqa.web.view.index;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(staticName = "of")
public class IndexFeed {
    private Integer feedThreadId;
    private String userImage;
    private String feedContent;
    private Integer countOfReply;
}
