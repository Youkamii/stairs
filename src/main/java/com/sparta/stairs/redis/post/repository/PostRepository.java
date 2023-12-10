package com.sparta.stairs.redis.post.repository;

import com.sparta.stairs.redis.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTitleContainingOrContentContainingOrderByModifiedAt(String titleKeyword, String contentKeyword);
    List<Post> findAllByUser_UsernameOrderByModifiedAt(String userId);
}

