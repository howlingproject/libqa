package com.libqa.web.service.search;

import com.google.common.collect.Lists;
import com.libqa.web.domain.*;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.service.qa.QaReplyService;
import com.libqa.web.service.user.UserService;
import com.libqa.web.view.feed.DisplayDate;
import com.libqa.web.view.search.DisplaySearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.libqa.application.util.LibqaConstant.ZERO;

@Component
public class DisplaySearchResultBuilder {
    @Autowired
    private UserService userService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private QaReplyService qaReplyService;

    List<DisplaySearchResult> buildByQaContent(List<QaContent> qaContents) {
        List<DisplaySearchResult> results = Lists.newArrayList();

        for (QaContent each : qaContents) {
            List<Keyword> keywords = keywordService.findByQaId(each.getQaId());
            User writer = userService.findByUserId(each.getUserId());

            DisplaySearchResult displaySearchResult = DisplaySearchResult.of();
            displaySearchResult.setId(each.getQaId());
            displaySearchResult.setTitle(each.getTitle());
            displaySearchResult.setContents(each.getContents());
            displaySearchResult.setUserNick(each.getUserNick());
            displaySearchResult.setUserImage(writer.getUserImage());
            displaySearchResult.setInsertDate(DisplayDate.parse(each.getInsertDate()));
            displaySearchResult.setCountOfReply(firstNonNull(qaReplyService.countByQaContent(each), ZERO));
            displaySearchResult.setKeywords(buildKeywords(keywords));
            results.add(displaySearchResult);
        }

        return results;
    }

    List<DisplaySearchResult> buildBySpace(List<Space> spaces) {
        List<DisplaySearchResult> results = Lists.newArrayList();

        for (Space each : spaces) {
            List<Keyword> keywords = keywordService.findBySpaceId(each.getSpaceId(), false);
            User writer = userService.findByUserId(each.getInsertUserId());

            DisplaySearchResult displaySearchResult = DisplaySearchResult.of();
            displaySearchResult.setId(each.getSpaceId());
            displaySearchResult.setTitle(each.getTitle());
            displaySearchResult.setContents(each.getDescription());
            displaySearchResult.setUserNick(each.getInsertUserNick());
            displaySearchResult.setUserImage(writer.getUserImage());
            displaySearchResult.setInsertDate(DisplayDate.parse(each.getInsertDate()));
            displaySearchResult.setKeywords(buildKeywords(keywords));
            results.add(displaySearchResult);
        }
        return results;
    }

    List<DisplaySearchResult> buildByWiki(List<Wiki> wikies) {
        List<DisplaySearchResult> results = Lists.newArrayList();

        for (Wiki each : wikies) {
            List<Keyword> keywords = keywordService.findByWikiId(each.getWikiId(), false);
            User writer = userService.findByUserId(each.getUserId());

            DisplaySearchResult displaySearchResult = DisplaySearchResult.of();
            displaySearchResult.setId(each.getWikiId());
            displaySearchResult.setTitle(each.getTitle());
            displaySearchResult.setContents(each.getContents());
            displaySearchResult.setUserNick(each.getUserNick());
            displaySearchResult.setUserImage(writer.getUserImage());
            displaySearchResult.setInsertDate(DisplayDate.parse(each.getInsertDate()));
            displaySearchResult.setCountOfReply(each.getReplyCount());
            displaySearchResult.setKeywords(buildKeywords(keywords));
            results.add(displaySearchResult);
        }

        return results;
    }

    private List<String> buildKeywords(List<Keyword> keywords) {
        List<String> result = Lists.newArrayList();
        for (Keyword each : keywords) {
            result.add(each.getKeywordName());
        }
        return result;
    }
}
