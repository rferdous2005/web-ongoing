package com.cirt.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirt.web.entity.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Integer>{
    
}
