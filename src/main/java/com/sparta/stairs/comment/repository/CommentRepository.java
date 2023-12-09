package com.sparta.stairs.comment.repository;

import com.sparta.stairs.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
