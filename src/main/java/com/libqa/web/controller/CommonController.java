package com.libqa.web.controller;

import com.libqa.application.dto.FileDto;
import com.libqa.application.enums.KeywordType;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.*;
import com.libqa.web.domain.KeywordList;
import com.libqa.web.domain.User;
import com.libqa.web.service.common.KeywordListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.libqa.application.enums.FileType.FILE;
import static com.libqa.application.enums.FileType.IMAGE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by yion on 2015. 3. 1..
 */
@Slf4j
@Controller
public class CommonController {
    @Value("${libqa.file.uploadPath}")
    private String serverUploadPath;

    @Autowired
    private KeywordListService keywordListService;

    @Autowired
    private LoggedUserManager loggedUserManager;

    // 403 Access Denied
    @RequestMapping(value = "/access", method = RequestMethod.GET)
    public ModelAndView accessDenied(Principal user) {
        ModelAndView model = new ModelAndView();

        log.info("####### user.role access ########");
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

        // 허용 파일 여부 체크
        FileUtil.allowedFile(uploadfile);

        // 이미지 파일일 경우 ContentType 검사
        FileUtil.checkImageUpload(uploadfile, viewType);

        log.debug("#### request ####");
        log.debug("# serverUploadPath = {}", serverUploadPath);

        try {
            // temp/userId/yyyyMMdd/ 에 일단 저장 후 차후 userId/yyyyMMdd/파일명 으로 이동 후 삭제해야 한다.
            User user = loggedUserManager.getUser();

            Integer userId = user.getUserId();
            String localDir = "/resource/temp/" + userId; // 임시 저장 경로
            String directory = serverUploadPath + localDir;  // 서버 저장 경로

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

            String filePath = Paths.get(serverUploadPath, localDir, fileDto.getSavedName()).toString();
            log.debug("## filePath : {}", filePath);

            // 파일 업로드 스트림
            FileUtil.fileUpload(uploadfile, filePath);

            log.debug("####### FILE SAVE INFO ########");
            log.debug("fileDto = {}", fileDto);
            return ResponseData.createSuccessResult(fileDto);
        } catch (Exception e) {
            log.error("# File Upload Error : {}", e);
            return ResponseData.createFailResult(fileDto);
        }
    }

    /**
     * 프로필 이미지 업로드 - 사용자 아이디로 폴더를 만들고 그 하위에 프로필 이미지를 적재한다. 단, 파일이 쌓이는 것을 방지 하기 위해
     * 해당 폴더는 삭제 후에 다시 생성한다. (/resource/profile/1/xxx.png)
     *
     * @param uploadfile
     * @param request
     * @return
     */
    @RequestMapping(value = "/common/userProfileImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> userProfileImage(@RequestParam("uploadfile") MultipartFile uploadfile,
                                            HttpServletRequest request) {
        String viewType = request.getParameter("viewType");
        FileDto fileDto = new FileDto();

        // 이미지 파일일 경우 ContentType 검사
        FileUtil.checkImageUpload(uploadfile, viewType);

        log.debug("#### request ####");
        log.debug("# request getServletContext().getRealPath = {}", request.getServletContext().getRealPath(FileUtil.SEPARATOR));
        log.debug("# request getServletPath = {} ", request.getServletPath());

        try {
            // /resource/profile/userId/yyyyMMdd/ 에 일단 저장 후 차후 userId/파일명 으로 이동 후 삭제해야 한다.
            User user = loggedUserManager.getUser();

            Integer userId = user.getUserId();
            String localDir = "/resource/profile/" + userId;
            String directory = serverUploadPath + localDir;  // 서버 저장 경로

            // 프로필 이미지 일단 삭제
            File deleteDir = new File(directory);
            if (deleteDir.exists()) {
                File[] files = deleteDir.listFiles();
                for (File file : files) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }

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

            String filePath = Paths.get(serverUploadPath, localDir, fileDto.getSavedName()).toString();

            String saveThumbFileName = "thumb_" + fileDto.getSavedName();

            String thumbTargetPath = directory + "/" + saveThumbFileName;

            log.debug("## filePath : {}", filePath);

            // 파일 업로드 스트림
            FileUtil.fileUpload(uploadfile, filePath);

            //원본 파일
            File orgFile = new File(filePath);

            //썸네일 파일 생성
            File thumbFile = new File(thumbTargetPath);

            fileDto = createThumbFile(orgFile, thumbFile, fileDto, localDir, saveThumbFileName);

            log.debug("####### FILE SAVE INFO ########");
            log.debug("fileDto = {}", fileDto);
            return ResponseData.createSuccessResult(fileDto);
        } catch (Exception e) {
            log.error("# File Upload Error : {}", e);
            return ResponseData.createFailResult(fileDto);
        }
    }

    private FileDto createThumbFile(File orgFile, File thumbFile, FileDto fileDto, String localDir, String saveThumbFileName) throws IOException {
        BufferedImage biImg = ImageIO.read(orgFile);

        int width = biImg.getWidth();
        int height = biImg.getHeight();

        log.debug("### 이미지의 원본 크기 : " + width + "/ " + height);

        int MAX_THUMB_SIZE = 64;
        if (width > MAX_THUMB_SIZE) {
            int y = (height * MAX_THUMB_SIZE) / width;
            width = MAX_THUMB_SIZE;
            height = y;
            log.debug("##이미지리사이징 후 : " + width + " / " + height);
        }

        GetThumbImage getThumbImage = new GetThumbImage();
        getThumbImage.resizeImage(orgFile, thumbFile, width, height);

        fileDto.setThumbPath(localDir);
        fileDto.setThumbName(saveThumbFileName);

        return fileDto;

    }

    public ResponseData<?> moveFileToProduct(FileDto fileDto) throws Exception {
        String savedDirectory = serverUploadPath + fileDto.getFilePath();
        String targetFileFullPath = savedDirectory + "/" + fileDto.getSavedName();

        User user = loggedUserManager.getUser();


        Integer userId = user.getUserId();
        String today = DateUtil.getToday();
        String localDir = "/resource/" + userId + "/" + today;  // 임시저장경로
        String movePath = this.serverUploadPath + localDir;    // 파일 이동 경로

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
            String directory = serverUploadPath + filePath;   // file 절대경로
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
    public ResponseData<KeywordList> findKeywordList(@RequestParam("keywordType") String keywordType) {
        boolean isDeleted = false;
        List<KeywordList> keywordList = new ArrayList<>();

        try {
            keywordList = keywordListService.findByKeywordType(keywordType, isDeleted);
            return ResponseData.createSuccessResult(keywordList);
        } catch (Exception e) {
            return ResponseData.createFailResult(keywordList);
        }
    }


    @RequestMapping(value = "/keyword/count", method = RequestMethod.GET)
    @ResponseBody
    public String keywordCount() {
        List<KeywordList> keywordLists = keywordListService.findByKeywordType(KeywordType.SPACE.getCode(), false);

        int keywordSize = 0;

        for (KeywordList keywordList : keywordLists) {
            keywordSize += keywordList.getKeywordCount();
        }

        return keywordSize + "";
    }


    /**
     * 해당 경로의 파일을 이미지로 내려준다.
     *
     * @param path
     * @return image file
     * @throws IOException
     */
    @RequestMapping(value = "/imageView", method = GET, produces = IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] imageView(@RequestParam String path) throws IOException {
        InputStream inputStream = getInputStream(path);
        try {
            return IOUtils.toByteArray(inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 해당 경로의 파일을 다운로드 한다.
     *
     * @param path
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/download", method = GET)
    public void download(@RequestParam String path, HttpServletResponse response) throws IOException {
        InputStream inputStream = getInputStream(path);

        try {
            byte[] data = IOUtils.toByteArray(inputStream);

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + generateDownloadFileName(path));
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");

            IOUtils.write(data, response.getOutputStream());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    private String generateDownloadFileName(@RequestParam String path) {
        try {
            return path.split(FileUtil.SEPARATOR)[path.split(FileUtil.SEPARATOR).length - 1];
        } catch (Exception e) {
            throw new IllegalArgumentException("Bad file path. [ " + path + " ]");
        }
    }

    private InputStream getInputStream(String path) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(serverUploadPath + path));
    }

}
