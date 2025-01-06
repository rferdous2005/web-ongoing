package com.cirt.web.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaDto {

    @Id
    private int id;
    private String fileName, fileExtension, description;
    MultipartFile file;
}
