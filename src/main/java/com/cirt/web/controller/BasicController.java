package com.cirt.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cirt.web.entity.Media;
import com.cirt.web.repository.MediaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping
public class BasicController {
    @Value("${app.file.location}")
    private String UPLOAD_DIR;

    @Autowired
    MediaRepository mediaRepository;

    @GetMapping
    public String getMethodName() {
        return "home";
    }
    
    @GetMapping("/who-we-are")
    public String getIntroWho() {
        return "basic/who";
    }

    @GetMapping("/what-we-do")
    public String getIntroWhat() {
        return "basic/what";
    }

    @GetMapping("/mission-vision")
    public String getIntroMisVis() {
        return "basic/mis-vis";
    }
    
    @GetMapping("/media")
    public void viewFile(HttpServletResponse response, HttpServletRequest request, @RequestParam("id") String fileName) throws Exception {
        //Media media = mediaRepository.findByFileName(fileName).orElseThrow(()->new Exception("No document Found!"));

        // logger.error("UPLOADED_FOLDER: " + GlobalConstants.UPLOADED_FOLDER);

        File file = new File( UPLOAD_DIR + fileName);
        FileInputStream inStream = new FileInputStream(file);
        // gets MIME type of the file
        String mimeType = "application/octet-stream";
        OutputStream outStream = response.getOutputStream();


        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }

}
