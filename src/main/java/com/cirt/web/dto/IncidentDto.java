package com.cirt.web.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentDto {

    private String reportType;
    @Column(nullable = true, length = 100)
    private String name, orgName, contactName, domainIP;
    @Column(nullable = false, length = 50)
    private String email, phone, region, incidentType, affectedAsset, discovery, attackVector, impact, ongoing;
    @Column(nullable = false, length = 500)
    private String location, description, stepsTaken;
    MultipartFile files;

    private LocalDateTime incidentTime;
}
