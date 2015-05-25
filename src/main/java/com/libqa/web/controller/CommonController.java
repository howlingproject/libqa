package com.libqa.web.controller;

import com.libqa.application.dto.FileDto;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.DateUtil;
import com.libqa.application.util.FileUtil;
import com.libqa.application.util.StringUtil;
import com.libqa.web.domain.KeywordList;
import com.libqa.web.service.KeywordListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.libqa.application.enums.FileTypeEnum.FILE;
import static com.libqa.application.enums.FileTypeEnum.IMAGE;

/**
 * Created by yion on 2015. 3. 1..
 */
@Slf4j
@Controller
public class CommonController {

    @Autowired
    KeywordListService keywordListService;

    // 403 Access Denied
    @RequestMapping(value = "/access", method = RequestMethod.GET)
    public ModelAndView accessDenied(Principal user) {
        ModelAndView model = new ModelAndView();

        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.setViewName("/common/403");
        return model;
    }

    /**
     * /space/ajaxUpload.hbs 파일 참조 - File input 의 onChange 시점에 임시 저장하여 view에 정보를 넘겨준다.
     *
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
            fileDto.setFileType(FileUtil.checkImageFile(uploadfile) ? IMAGE : FILE);

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

    public ResponseData<?> moveFileToProduct(FileDto fileDto) throws Exception {
        String serverPath = fileDto.getRootPath();
        String savedDirectory = serverPath + fileDto.getFilePath();
        String targetFileFullPath = savedDirectory + "/" + fileDto.getSavedName();
        Integer userId = 1; // 추후 session에서 가지고 와야한다.
        String today = DateUtil.getToday();
        String localDir = "/resource/" + userId + "/" + today;  // 임시저장경로
        String movePath = serverPath + localDir;    // 파일 이동 경로

        try {
            File targetFile = new File(targetFileFullPath);
            if (targetFile.exists() && !targetFile.isDirectory()) {
                Path targetFilePath = Paths.get(targetFileFullPath);
                Path newPath = Paths.get(movePath);
                File newPathDir = new File(movePath);
                if (!newPathDir.exists() || !newPathDir.isDirectory()) {
                    newPathDir.mkdirs();
                }
                Files.move(targetFilePath, newPath.resolve(targetFilePath.getFileName()));
                fileDto.setFilePath(localDir);  // 이동한 파일경로 세팅
            } else {
                log.debug("파일이 존재하지 않거나 폴더 경로 입니다.");
            }
            return ResponseData.createSuccessResult(fileDto);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseData.createFailResult(fileDto);
        }
    }

    @RequestMapping(value = "/common/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> deleteFile(HttpServletRequest request) {
        String filePath = request.getParameter("filePath");
        String savedName = request.getParameter("savedName");
        FileDto fileDto = new FileDto();

        try {
            String serverPath = StringUtil.defaultString(request.getServletContext().getRealPath(FileUtil.SEPARATOR));

            String directory = serverPath + filePath;   // file 절대경로
            String fileFullPath = directory + "/" + savedName;
            File targetFile = new File(fileFullPath);

            if (targetFile.exists() && !targetFile.isDirectory()) {
                targetFile.delete();
            }
            return ResponseData.createSuccessResult(fileDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.createFailResult(fileDto);
        }
    }

    @RequestMapping(value = "/common/findKeywordList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<KeywordList> findKeywordList(@RequestParam("keywordType") String keywordType){
        boolean isDeleted = false;
        List<KeywordList> keywordList = new ArrayList<>();

        try {
            keywordList = keywordListService.findByKeywordType(keywordType, isDeleted);
            return ResponseData.createSuccessResult(keywordList);
        } catch (Exception e) {
            return ResponseData.createFailResult(keywordList);
        }
    }

}
