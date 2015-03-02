package com.libqa.application.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yion on 2015. 3. 2..
 */
@Slf4j
public class FileUtil {

    /**
     * 업로드가 허용되지 않는 파일일 경우 true 를 리턴한다.
     * 확장자 만으로는 파일을 막을 수 없으므로 다른 방식의 보안 처리를 고려해야 한다.
     * @param file
     */
    public static void notAllowedFile(MultipartFile file) {
        final String[] notAccess = {"jsp", "php", "asp", "aspx", "html", "htm", "js", "vb", "perl", "cpp", "dat", "java", "class", "css"};

        int dotIndex = file.getOriginalFilename().lastIndexOf('.');
        String extensionType = file.getOriginalFilename().substring(dotIndex + 1);

        int lengths = notAccess.length;
        for (int i = 0 ; i < lengths ; i++) {
            if (extensionType.equalsIgnoreCase(notAccess[i])) {
                log.error("#Error File Extension = {}, Contents Type : {}", extensionType, file.getContentType());
                throw new IllegalStateException("허용된 파일 확장자가 아닙니다.");
            }
        }
    }


    /**
     * 이미지 파일일 경우 true 를 리턴한다.
     * @param file
     * @return
     */
    public static boolean checkImageFile(MultipartFile file) {
        final String[] contents = {"image/png", "image/jpeg", "image/jpg", "image/gif"};
        String fileContentType = file.getContentType();
        int lengths = contents.length;
        for (int i = 0 ; i < lengths ; i++) {
            if (fileContentType.equalsIgnoreCase(contents[i])) {
                return true;
            }
        }
        return false;
    }


}
