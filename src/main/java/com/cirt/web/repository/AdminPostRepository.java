package com.cirt.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cirt.web.entity.AdminPost;

@Repository
public interface AdminPostRepository extends JpaRepository<AdminPost, Long> {
}
