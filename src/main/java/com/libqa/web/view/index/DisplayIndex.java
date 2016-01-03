package com.libqa.web.view.index;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DisplayIndex {
    private List<IndexQa> qas;
    private List<IndexWiki> wikies;
    private List<IndexSpace> spaces;
    private List<IndexFeed> feeds;
}
