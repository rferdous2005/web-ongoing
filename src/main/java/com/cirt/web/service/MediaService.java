package com.cirt.web.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Value("${app.file.location}")
    private static String UPLOAD_DIR;

    public void addMedia(MediaDto mediaDto){
        String filePath = UPLOAD_DIR + mediaDto.getFileExtension() + "/" + mediaDto.getFileName()+mediaDto.getFileExtension();
        if (!mediaDto.getFile().isEmpty() ) {
            try {
                byte[] bytes = mediaDto.getFile().getBytes();
                filePath = Files.exists(Paths.get(filePath)) ? 
                mediaDto.getFileExtension() + "/" + mediaDto.getFileName()+"-"+System.currentTimeMillis()/1000000+mediaDto.getFileExtension() : filePath;
                Path path = Paths.get(filePath);
                Path p = Files.write(path, bytes);
                System.out.println(p.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Media media = new Media();
        media.setFileName(filePath);
        media.setFileExtension(mediaDto.getFileExtension());
        media.setDescription(mediaDto.getDescription());
        mediaRepository.save(media);
    }
    
    public Page<Media> getPaginatedMedias(Pageable pageable) {
        return mediaRepository.findAll(pageable);
    }
}
