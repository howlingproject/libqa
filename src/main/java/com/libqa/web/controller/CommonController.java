package com.libqa.web.controller;

import com.libqa.application.Exception.FilePermitMsgException;
import com.libqa.application.dto.FileDto;
import com.libqa.application.enums.StatusCodeEnum;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yion on 2015. 3. 1..
 */
@Controller
@Slf4j
public class CommonController {


    @Value("${file.maxsize}")
    String uploadMaxSize;


    private static String SEPARATOR = "/";
    private static String UPLOAD_TYPE_IMAGE = "image";


    @RequestMapping(value = "/common/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> fileUpload(@RequestParam("uploadfile") MultipartFile uploadfile,
                                      HttpServletRequest request) {
        String viewType = request.getParameter("viewType");
        FileDto fileDto = new FileDto();
        ResponseData data = new ResponseData();

        String serverPath = request.getServletContext().getRealPath(SEPARATOR);

        // viewType 이 Space, Editor, Feed 인 경우 이미지만 허용한다.
        boolean isAllowedFile = checkAllowedImageFormat(uploadfile.getContentType(), viewType);

        if (!isAllowedFile) {
            data.setComment("이미지만 허용됩니다.");
            data.setResultCode(StatusCodeEnum.INVALID_PARAMETER.getCode());
            data.setData(viewType);
            return data;
        }

        log.info("#### request ####");
        log.info("# request getServletContext().getRealPath = {}", request.getServletContext().getRealPath(SEPARATOR));
        log.info("# request getServletPath = {} ", request.getServletPath());
        log.info("# request viewType = {} ", viewType);

        try {
            // temp/userId/yyyyMMdd/ 에 일단 저장 후 차후 userId/yyyyMMdd/파일명 으로 이동 후 삭제해야 한다.
            Integer userId = 1;
            String today = DateUtil.getToday();
            double maxSize = Double.parseDouble(uploadMaxSize);

            if (uploadfile.getSize() > (maxSize * 1024 * 1024)) {
                throw new FilePermitMsgException("파일 크기가 " + maxSize + "MB를 초과합니다.");
            }


            log.info("## uploadfile : {}", uploadfile.getContentType());
            log.info("## uploadfile : {}", uploadfile.getName());
            log.info("## uploadfile : {}", uploadfile.getOriginalFilename());
            log.info("## uploadfile : {}", uploadfile.getSize());
            String[] fileType = uploadfile.getContentType().split("/");

            String fileOriginalName = uploadfile.getOriginalFilename();
            //업로드 파일 확장자 취득
            int dotIndex = fileOriginalName.lastIndexOf('.');
            String extendName = fileOriginalName.substring(dotIndex + 1);
            String temp = String.valueOf(System.nanoTime());
            //저장 파일명
            String saveFileName = today+temp+"."+extendName.toLowerCase();


            String localDir = "/resource/temp/"+userId;
            String directory = serverPath + localDir;

            // 폴더 생성
            File baseDir = new File(directory);
            if (!baseDir.exists() || !baseDir.isDirectory()) {
                baseDir.mkdirs();
            } else {
                log.debug("##### " + directory + "위치에 디렉토리가 존재합니다.");
            }

            String filePath = Paths.get(serverPath, localDir, saveFileName).toString();
            log.debug("## filePath : {}", filePath);


            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(uploadfile.getBytes());
            stream.close();

            fileDto.setFilePath(localDir);
            fileDto.setFileSize(uploadfile.getSize());
            fileDto.setFileExtendType(extendName);
            fileDto.setRealName(fileOriginalName);
            fileDto.setSavedName(saveFileName);
            fileDto.setUploadType(fileType[0]);       // image 여부 판단

            //TODO
            //image (profile) 일 경우 thumbnail 생성 로직 추가 되어야 함

            log.info("####### FILE SAVE INFO ########");
            log.info("fileDto = {}", fileDto);
            data.setComment(StatusCodeEnum.SUCCESS.getComment());
            data.setResultCode(StatusCodeEnum.SUCCESS.getCode());
            data.setData(fileDto);
        } catch (Exception e) {
            log.error("# File Upload Error : {}", e);
            data.setComment("FAIL");
            data.setResultCode(StatusCodeEnum.FAIL.getCode());
            data.setData(fileDto);
        }

        return data;

    }

    private boolean checkAllowedImageFormat(String fileFormat, String viewType) {
        List allowedFileFormat = new ArrayList<>();
        allowedFileFormat.add("image/png");
        allowedFileFormat.add("image/jpeg");
        allowedFileFormat.add("image/jpg");
        allowedFileFormat.add("image/gif");

        log.debug("# fileFormat : " + fileFormat);
        log.debug("# viewType : " + viewType);

        // 공간 생성 혹은 에디터일 경우 이미지만 허용된다.
        if (viewType.equals("Space") || viewType.equals("Editor") || viewType.equals("Feed")) {
            if (allowedFileFormat.contains(fileFormat)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


}
