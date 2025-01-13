package com.cirt.web.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 20)
    private String category;

    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "sub_title", nullable = false, length = 300)
    private String subTitle;

    @Column(name = "uri", nullable = false, length = 300)
    private String uri;

    //@Column(name = "body", nullable = false, length = 65535)
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String body;

    @Column(name = "visibility", nullable = false, length = 10)
    private String visibility;

    LocalDateTime publishedAt;

    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false, length = 50)
    private String createdBy;
}
