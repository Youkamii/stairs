package com.sparta.stairs.commentlike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.stairs.commentlike.entity.CommentLike;
import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.user.entity.User;

import com.sparta.stairs.comment.entity.Comment;


import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUserAndPostAndComment(User user, Post post, Comment comment);
}

