package com.libqa.web.service;

import com.libqa.application.util.FileHandler;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.service.feed.FeedFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeedFileServiceTest {
    @Mock
    private FeedFileRepository feedFileRepository;
    @Mock
    private FileHandler fileHandler;

    @InjectMocks
    private FeedFileService sut = new FeedFileService();

    @Test
    public void save() {
        FeedFile feedFile = new FeedFile();

        sut.save(feedFile);

        verify(feedFileRepository).save(any(FeedFile.class));
    }

    @Test
    public void delete() {
        FeedFile feedFile = new FeedFile();
        feedFile.setDeleted(false);

        sut.delete(feedFile);

        assertThat(feedFile.isDeleted()).isTrue();
        verify(fileHandler).delete(anyString());
    }

}
