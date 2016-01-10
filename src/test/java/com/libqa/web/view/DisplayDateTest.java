package com.libqa.web.view;

import com.libqa.web.view.feed.DisplayDate;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class DisplayDateTest {

    @Test
    public void parse() throws Exception {
        final LocalDateTime localDateTime = LocalDateTime.of(2015, 11, 15, 23, 29, 30);
        final Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        String actual = DisplayDate.parse(date);

        assertThat(actual).isEqualTo("2015-11-15 23:29:30");
    }
}