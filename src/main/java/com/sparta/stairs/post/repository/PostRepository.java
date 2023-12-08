package com.sparta.stairs.post.repository;

import com.sparta.stairs.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedDateTimeDesc();

    List<Post> findByTitleOrderByCreatedDateTimeDesc(String 제목);
}
