package com.sparta.stairs.postlike.repository;

import com.sparta.stairs.postlike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.stairs.redis.post.entity.Post;
import com.sparta.stairs.user.entity.User;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByUserAndPost(User user, Post post);
}
