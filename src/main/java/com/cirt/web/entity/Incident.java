package com.cirt.web.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reportType;
    @Column(nullable = true, length = 100)
    private String name, orgName, contactName, domainIP, fileName;
    @Column(nullable = false, length = 50)
    private String email, phone, region, incidentType, affectedAsset, discovery, attackVector, impact, ongoing, responseStatus, generatedId;
    @Column(nullable = false, length = 500)
    private String location, description, stepsTaken;

    private LocalDateTime incidentTime;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
