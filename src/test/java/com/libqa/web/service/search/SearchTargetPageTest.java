package com.libqa.web.service.search;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SearchTargetPageTest {

    @Test
    public void isInValidatePage() {
        boolean result = SearchTargetPage.get("space1234").isInValidPage();
        assertThat(result).isTrue();
    }


    @Test
    public void isValidatePage() {
        boolean result = SearchTargetPage.get("space").isInValidPage();
        assertThat(result).isFalse();
    }


}