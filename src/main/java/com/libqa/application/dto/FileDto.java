package com.libqa.application.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * File Upload 속성 정의
 * Created by yion on 2015. 3. 1..
 */
@Data
public class FileDto {

    private MultipartFile multipartFile;

    private String realName;

    private String savedName;

    private String rootPath;

    private String filePath;

    private Integer fileSize;

    private String fileType;
}
