package com.libqa.application.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FileHandlerTestConfig.class)
public class FileHandlerTest {

    @Autowired
    private FileHandler sut;

    @Test
    public void serverUploadPath() {
        assertThat(sut.getServerUploadPath()).isEqualTo("~/libqa/files");
    }

    @Test
    public void generateDownloadFileName()  {
        final String filePath = "resources/tmp/7/file.doc";

        String downloadFileName = sut.generateDownloadFileName(filePath);

        assertThat(downloadFileName).isEqualTo("file.doc");
    }

}