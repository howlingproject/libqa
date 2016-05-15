package com.libqa.web.service.search;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.User;
import com.libqa.web.repository.SearchQaRepository;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.service.qa.QaReplyService;
import com.libqa.web.service.user.UserService;
import com.libqa.web.view.feed.DisplayDate;
import com.libqa.web.view.search.DisplaySearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.MoreObjects.firstNonNull;

@Slf4j
@Service
public class SearchQaService {
    private static final Integer ZERO = 0; // TODO constant

    @Autowired
    private SearchQaRepository searchQaRepository;
    @Autowired
    private QaReplyService qaReplyService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private UserService userService;

    public List<DisplaySearchResult> resolve(String query) {
        log.debug("search query : {}", query);
        List<QaContent> qaContents = searchQaRepository.search(query);
        return buildDisplaySearchResult(qaContents);
    }

    private List<DisplaySearchResult> buildDisplaySearchResult(List<QaContent> qaContents) {
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

    private List<String> buildKeywords(List<Keyword> keywords) {
        List<String> result = Lists.newArrayList();
        for (Keyword each : keywords) {
            result.add(each.getKeywordName());
        }
        return result;
    }
}
