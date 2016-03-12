package com.libqa.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ImageController {
    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/imageView", method = GET, produces = IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] imageView(@RequestParam String path) throws IOException {
        InputStream inputStream = servletContext.getResourceAsStream(path);
        try {
            return IOUtils.toByteArray(inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
