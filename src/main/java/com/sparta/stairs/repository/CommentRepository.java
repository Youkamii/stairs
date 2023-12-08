package com.sparta.stairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository {
    List<Comment> findAllByPostIdOrderByCreateDateTimeDesc(Long postId);
}