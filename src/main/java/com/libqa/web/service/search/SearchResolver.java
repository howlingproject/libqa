package com.libqa.web.service.search;

import com.google.common.collect.Lists;
import com.libqa.web.view.search.DisplaySearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchResolver {

    public List<DisplaySearchResult> resolve() {
        return Lists.newArrayList();
    }
}
