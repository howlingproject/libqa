package com.libqa.web.view.space;

import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.User;
import com.libqa.web.domain.Wiki;
import lombok.*;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 8. 23.
 * @Description :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceWikiList {
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
    private List<SpaceWiki> spaceWikiList;
}
