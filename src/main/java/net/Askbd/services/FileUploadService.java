package net.Askbd.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService {

    @Value("${document.upload.directory:/upload}")
    private String UPLOAD_DIR;
    
}