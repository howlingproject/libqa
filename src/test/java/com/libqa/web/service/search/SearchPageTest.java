package com.libqa.web.service.search;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SearchPageTest {

    @Test
    public void findPageName() {
        String pageName = SearchPage.findPageName("space");
        assertThat(pageName).isEqualTo("space");
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotFoundPageName() {
        SearchPage.findPageName("badPageName");
    }

}