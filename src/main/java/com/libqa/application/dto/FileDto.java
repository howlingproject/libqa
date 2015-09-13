package com.libqa.application.dto;

import com.libqa.application.enums.FileType;
import lombok.Data;

/**
 * File Upload 속성 정의
 * Created by yion on 2015. 3. 1..
 */
@Data
public class FileDto {

    private String realName;

    private String savedName;

    private String rootPath;

    private String filePath;

    private double fileSize;

    private String fileExtendType;

    private String uploadType;

    private FileType fileType;
}
