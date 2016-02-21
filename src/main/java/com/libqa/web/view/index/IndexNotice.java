package com.libqa.web.view.index;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(staticName = "of")
public class IndexNotice {
    private static final IndexNotice EMPTY = IndexNotice.of();

    private String viewUrl;
    private String moreUrl;
    private String title;
    private String contents;
    private String insertDate;
    private int countOfReply;

    public static IndexNotice empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
