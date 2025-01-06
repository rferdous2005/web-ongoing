package com.cirt.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirt.web.entity.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    
}
