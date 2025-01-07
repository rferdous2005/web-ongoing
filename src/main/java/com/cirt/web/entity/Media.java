package com.cirt.web.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fileName, fileExtension, description;

    // Automatically set the creation timestamp when the entity is persisted
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Optional: Add a formatted getter if needed for custom formatting (e.g., for templates)
    public String getFormattedCreatedAt() {
        return createdAt != null ? createdAt.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm:ss")) : null;
    }

}
