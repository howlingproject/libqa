package com.libqa.web.view;

import com.libqa.web.domain.WikiLike;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by songanji on 2015. 12. 6..
 */
@Data
@AllArgsConstructor
public class DisplayWikiLike {
    private Integer result;
    private WikiLike wikiLike;
}
