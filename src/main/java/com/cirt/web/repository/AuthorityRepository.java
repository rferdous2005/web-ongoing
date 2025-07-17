package com.cirt.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirt.web.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

}