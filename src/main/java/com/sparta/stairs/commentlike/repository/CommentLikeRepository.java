package com.sparta.stairs.commentlike.repository;

import com.sparta.stairs.commentlike.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUserAndPostAndComment(User user, Post post, Comment comment);
}

