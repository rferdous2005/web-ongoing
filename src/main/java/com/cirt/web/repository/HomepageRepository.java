package com.cirt.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirt.web.entity.Homepage;

@Repository
public interface HomepageRepository extends JpaRepository<Homepage, Integer> {
    
}
