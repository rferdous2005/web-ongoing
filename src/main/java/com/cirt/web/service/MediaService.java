package com.cirt.web.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cirt.web.dto.MediaDto;
import com.cirt.web.entity.Media;
import com.cirt.web.repository.MediaRepository;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;
    // @Autowired
    // private Media media;

    private static final String UPLOAD_DIR = "/media-uploads/";

    public void addMedia(MediaDto mediaDto){
        if (!mediaDto.getFile().isEmpty() ) {
            try {
                byte[] bytes = mediaDto.getFile().getBytes();
                Path path = Paths.get(UPLOAD_DIR + mediaDto.getFileExtension() + "/" + mediaDto.getFileName()+mediaDto.getFileExtension());
                Path p = Files.write(path, bytes);
                System.out.println(p.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Media media = new Media();
        media.setFileName(mediaDto.getFileName());
        media.setFileExtension(mediaDto.getFileExtension());
        media.setDescription(mediaDto.getDescription());
        mediaRepository.save(media);
    }
    
}
