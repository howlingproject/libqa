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


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> fileUpload(@RequestParam("uploadfile") MultipartFile uploadfile, @RequestParam("viewType") String viewType, HttpServletRequest request) {
        FileDto fileDto = new FileDto();
        ResponseData data = new ResponseData();

        String serverPath = request.getServletContext().getRealPath(SEPARATOR);

        boolean isAllowedFile = checkAllowedImageFormat(uploadfile.getContentType(), viewType);

        if (!isAllowedFile) {
            data.setComment("이미지만 허용됩니다.");
            data.setStatusCode(StatusCodeEnum.INVALID_PARAMETER);
            data.setData(viewType);
            return data;
        }

        log.info("#### request ####");
        log.info("# request getServletContext().getRealPath = {}", request.getServletContext().getRealPath(SEPARATOR));
        log.info("# request getServletPath = {} ", request.getServletPath());

        try {
            // yyyyMMdd
            Integer userId = 1;
            String today = DateUtil.getToday();
            double maxSize = Double.parseDouble(uploadMaxSize);

            log.info("## uploadfile : {}", uploadfile.getContentType());
            log.info("## uploadfile : {}", uploadfile.getName());
            log.info("## uploadfile : {}", uploadfile.getOriginalFilename());
            log.info("## uploadfile : {}", uploadfile.getSize());

            String filename = uploadfile.getOriginalFilename();
            String filepath = Paths.get(serverPath, filename).toString();

            if (uploadfile.getSize() > (maxSize  * 1024 * 1024)) {
                throw new FilePermitMsgException("파일 크기가 " + maxSize + "MB를 초과합니다.");
            }


            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();

            data.setComment("SUCCESS");
            data.setStatusCode(StatusCodeEnum.SUCCESS);
            data.setData(fileDto);
        } catch (Exception e) {
            log.error("# File Upload Error : {}", e);
            data.setComment("FAIL");
            data.setStatusCode(StatusCodeEnum.FAIL);
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
