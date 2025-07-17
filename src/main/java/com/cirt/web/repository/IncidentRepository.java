package com.cirt.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirt.web.entity.Incident;
import com.cirt.web.entity.Post;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Integer>{
    Page<Incident> findAll(Pageable pageable);
}
