package com.libqa.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class FileController {

    private static final String SLASH = "/";

    @Autowired
    private ServletContext servletContext;

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
            response.setHeader("Content-Disposition", "attachment; filename=" + generateFileName(path));
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");

            IOUtils.write(data, response.getOutputStream());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    private String generateFileName(@RequestParam String path) {
        try {
            return path.split(SLASH)[path.split(SLASH).length - 1];
        } catch (Exception e) {
            throw new IllegalArgumentException("Bad file path. [ " + path + " ]");
        }
    }

    private InputStream getInputStream(@RequestParam String path) throws FileNotFoundException {
        InputStream inputStream = servletContext.getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("Cannot found file. [ " + path + " ]");
        }
        return inputStream;
    }
}
