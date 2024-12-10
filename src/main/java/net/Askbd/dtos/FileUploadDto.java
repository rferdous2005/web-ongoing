package net.Askbd.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadDto {
    private String description, altText;
    private MultipartFile file;
}
