package com.libqa.application.dto;

import com.libqa.application.enums.FileType;
import lombok.Data;

/**
 * File Upload 속성 정의
 * Created by yion on 2015. 3. 1..
 */
@Data
public class FileDto {

    private String realName;    // 실제 파일명

    private String savedName;   // 저장 파일 명

    private String rootPath;    // 경로

    private String filePath;    // 파일 경로

    private double fileSize;    // 파일 사이즈

    private String fileExtendType;  // 확장자 타입

    private String uploadType;  // 업로드 타입

    private FileType fileType;  // 파일 타입

    private String thumbYn;		 // 섬네일 유무

    private String thumbPath;	 // 섬네일 패스

    private String thumbName;	 // 섬네일 파일 명


}
