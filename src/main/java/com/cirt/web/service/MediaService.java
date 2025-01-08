package com.cirt.web.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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

    @Value("${app.file.location}")
    private String UPLOAD_DIR;

    public void addMedia(MediaDto mediaDto) {
        String fullFilePath = UPLOAD_DIR + mediaDto.getFileExtension() + "/" + mediaDto.getFileName() + mediaDto.getFileExtension();
        String filePath = mediaDto.getFileExtension() + "/" + mediaDto.getFileName() + mediaDto.getFileExtension();
        if (!mediaDto.getFile().isEmpty()) {
            try {
                byte[] bytes = mediaDto.getFile().getBytes();
                filePath = Files.exists(Paths.get(fullFilePath)) 
                        ? mediaDto.getFileExtension() + "/" + mediaDto.getFileName() + "-" + System.currentTimeMillis() / 1000000 + mediaDto.getFileExtension()
                        : filePath;
                Path path = Paths.get(UPLOAD_DIR + filePath);
                Path p = Files.write(path, bytes);
                System.out.println(p.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create and save media with the current timestamp
        Media media = new Media();
        media.setFileName(filePath);
        media.setFileExtension(mediaDto.getFileExtension());
        media.setDescription(mediaDto.getDescription());
        //media.setCreatedAt(LocalDateTime.now()); // Automatically set the current time
        mediaRepository.save(media);
    }

    public Page<Media> getPaginatedMedias(Pageable pageable) {
        return mediaRepository.findAll(pageable);
    }

    public void deleteMedia(int fileId) {
        Media media = mediaRepository.findById(fileId).get();
        mediaRepository.delete(media);
        String filePath = UPLOAD_DIR+media.getFileName();
        System.out.println(filePath);
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
