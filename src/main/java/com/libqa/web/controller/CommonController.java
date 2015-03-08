package com.libqa.web.controller;

import com.libqa.application.dto.FileDto;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.FileUtil;
import com.libqa.application.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;

/**
 * Created by yion on 2015. 3. 1..
 */
@Controller
@Slf4j
public class CommonController {


    /**
     * /space/ajaxUpload.hbs 파일 참조 - File input 의 onChange 시점에 임시 저장하여 view에 정보를 넘겨준다.
     * @param uploadfile
     * @param request
     * @return
     */
    @RequestMapping(value = "/common/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> fileUpload(@RequestParam("uploadfile") MultipartFile uploadfile,
                                      HttpServletRequest request) {

        String viewType = request.getParameter("viewType");
        FileDto fileDto = new FileDto();
        String serverPath = StringUtil.defaultString(request.getServletContext().getRealPath(FileUtil.SEPARATOR));

        // 허용 파일 여부 체크
        FileUtil.allowedFile(uploadfile);
        // 이미지 파일일 경우 ContentType 검사
        FileUtil.checkImageUpload(uploadfile, viewType);

        log.info("#### request ####");
        log.info("# request getServletContext().getRealPath = {}", request.getServletContext().getRealPath(FileUtil.SEPARATOR));
        log.info("# request getServletPath = {} ", request.getServletPath());

        try {
            // temp/userId/yyyyMMdd/ 에 일단 저장 후 차후 userId/yyyyMMdd/파일명 으로 이동 후 삭제해야 한다.
            Integer userId = 1; // 추후 사용자 순번
            String localDir = "/resource/temp/" + userId; // 임시 저장 경로
            String directory = serverPath + localDir;  // 서버 저장 경로

            // 폴더 생성
            File baseDir = new File(directory);
            if (!baseDir.exists() || !baseDir.isDirectory()) {
                baseDir.mkdirs();
            } else {
                log.debug("##### " + directory + "위치에 디렉토리가 존재합니다.");
            }

            // 파일 정보 추출
            fileDto = FileUtil.extractFileInfo(uploadfile, localDir);
            fileDto.setFilePath(localDir);

            String filePath = Paths.get(serverPath, localDir, fileDto.getSavedName()).toString();
            log.debug("## filePath : {}", filePath);

            // 파일 업로드 스트림
            FileUtil.fileUpload(uploadfile, filePath);

            log.info("####### FILE SAVE INFO ########");
            log.info("fileDto = {}", fileDto);
            return ResponseData.createSuccessResult(fileDto);
        } catch (Exception e) {
            log.error("# File Upload Error : {}", e);
            return ResponseData.createFailResult(fileDto);
        }

    }

}
