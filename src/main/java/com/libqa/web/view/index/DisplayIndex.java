package com.libqa.web.view.index;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(staticName = "of")
public class DisplayIndex {
    private List<IndexQaContent> qaContents;
    private List<IndexWiki> wikies;
    private List<IndexSpace> spaces;
    private List<IndexFeed> feeds;
}
