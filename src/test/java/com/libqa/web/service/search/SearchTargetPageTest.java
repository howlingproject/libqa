package com.libqa.web.service.search;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SearchTargetPageTest {

    @Test
    public void isValidateKey() {
        boolean result = SearchTargetPage.isValidPage("space");
        assertThat(result).isTrue();
    }

    @Test
    public void isInvalidPageKey() {
        boolean result = SearchTargetPage.isValidPage("badPageKey");
        assertThat(result).isFalse();
    }

}