package com.libqa.application.util;

import com.libqa.application.dto.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yion on 2015. 3. 2..
 */
@Slf4j
public class FileUtil {

    public static String SEPARATOR = "/";

    /**
     * 확장자 체크
     */
    public static final String[] PERMITTED_EXTS = new String[] {
            "DOC", "DOCX", "XLS", "XLSX", "PPT", "PPTX", "PPS", "HWP", "TXT", "PDF",
            "ZIP", "ALZ", "JAR",  "AI", "PSD", "XMIND",
            "AVI", "MOV", "MPG", "MPEG", "RM", "ASF", "WMV", "MP3", "MP4",
            "BMP", "JPG", "JPEG", "GIF", "PNG", "FLA", "SWF", "HTM", "HTML" , "HTML",
            "XML", "WAR", "JAR", "TAR.GZ", "GZ", "DMG", "GDOC", "GSHEET", "GSLIDES",
            "DMG", "APK"
    };



    /**
     * MultipartFile 정보를 추출하여 FileDto 에 저장한다.
     * @param uploadfile
     * @param localDir
     * @return
     * @throws Exception
     */
    public static FileDto extractFileInfo(MultipartFile uploadfile, String localDir) throws Exception {
        FileDto fileDto = new FileDto();
        String today = DateUtil.getToday();

        log.info("## uploadfile : {}", uploadfile.getContentType());
        log.info("## uploadfile : {}", uploadfile.getName());
        log.info("## uploadfile : {}", uploadfile.getOriginalFilename());
        log.info("## uploadfile : {}", uploadfile.getSize());
        String[] fileType = uploadfile.getContentType().split(SEPARATOR);

        String fileOriginalName = uploadfile.getOriginalFilename();
        //업로드 파일 확장자 취득
        int dotIndex = fileOriginalName.lastIndexOf('.');
        String extendName = fileOriginalName.substring(dotIndex + 1);
        String temp = String.valueOf(System.nanoTime());

        //저장 파일명 (ex : 2015030214332087117027.jpg)
        String saveFileName = today + temp + "." + extendName.toLowerCase();
        double fileSize = uploadfile.getSize();
        String uploadType = fileType[0];

        fileDto.setFilePath(localDir);
        fileDto.setFileSize(fileSize);
        fileDto.setFileExtendType(extendName);
        fileDto.setRealName(fileOriginalName);
        fileDto.setSavedName(saveFileName);
        fileDto.setUploadType(uploadType);
        return fileDto;
    }



    /**
     * 업로드 허용파일 목록
     * @param file
     */
    public static void allowedFile(MultipartFile file) {
        int dotIndex = file.getOriginalFilename().lastIndexOf('.');
        String ext = file.getOriginalFilename().substring(dotIndex + 1);
        boolean result = false;
        for (int i = 0; i < PERMITTED_EXTS.length; i++) {
            if (ext.equalsIgnoreCase(PERMITTED_EXTS[i])) {
                result = true;
                break;
            }
        }

        if (!result) {
            log.error("#Error File Extension = {}, Contents Type : {}", ext, file.getContentType());
            throw new IllegalStateException("허용된 파일 확장자가 아닙니다.");
        }
    }


    /**
     * 이미지 파일일 경우 true 를 리턴한다.
     * @param uploadfile
     * @param viewType
     * @return
     */
    public static boolean checkImageUpload(MultipartFile uploadfile, String viewType) {
        boolean isImage = false;
        if (StringUtil.defaultString(viewType).equals("Image")) { // viewType 이 Image인 경우 (Space, Wiki의 에디터인 경우) 이미지 파일만 허용한다.
            isImage = checkImageFile(uploadfile);
            if (!isImage) {
                throw new IllegalStateException("이미지 파일만 업로드가 허용됩니다.");
            }
        }
        return isImage;
    }

    public static boolean checkImageFile(MultipartFile file) {
        final String[] contentsType = {"image/png", "image/jpeg", "image/jpg", "image/gif"};
        String fileContentType = file.getContentType();
        int lengths = contentsType.length;
        for (int i = 0 ; i < lengths ; i++) {
            if (fileContentType.equalsIgnoreCase(contentsType[i])) {
                return true;
            }
        }
        return false;
    }


    public static  void fileUpload(MultipartFile uploadfile, String path) throws IOException {
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(path)));
        stream.write(uploadfile.getBytes());
        stream.close();
    }

    public static void makeThumbnailAndUpload(MultipartFile uploadfile) {


    }
}
