package com.libqa.application.util;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HtmlContentHandlerTest {

    @Test
    public void urlWithLink() {
        final String url = "https://www.google.com?q=1234&q2=abcd";
        String actual = new HtmlContentHandler(url).urlWithLink().parse();
        assertThat(actual).isEqualTo("<a href=\"https://www.google.com?q=1234&amp;q2=abcd\" target=\"_blank\" " +
                "rel=\"nofollow\">https://www.google.com?q=1234&amp;q2=abcd</a>");
    }

    @Test
    public void nl2br() {
        final String text = "line1\nline2";
        String actual = new HtmlContentHandler(text).nl2br().parse();
        assertThat(actual).isEqualTo("line1<br />line2");
    }
}