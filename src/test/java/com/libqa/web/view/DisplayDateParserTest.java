package com.libqa.web.view;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class DisplayDateParserTest {

    @Test
    public void parseForFeed() throws Exception {
        final LocalDateTime localDateTime = LocalDateTime.of(2015, 11, 15, 23, 29, 30);
        final Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        String actual = DisplayDateParser.parseForFeed(date);

        assertThat(actual).isEqualTo("2015-11-15 23:29:30");
    }
}