package com.sparta.stairs.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.stairs.comment.entity.Comment;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String writer;
    private final String commentText;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ZonedDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ZonedDateTime modifiedAt;
    private final int likeCount;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getUser().getUserId();
        this.commentText = comment.getCommentText();

        if (!comment.getCreatedAt().equals(comment.getModifiedAt())) {
            this.modifiedAt = comment.getModifiedAt();
        } else {
            this.createdAt = comment.getCreatedAt();
        }

        this.likeCount = comment.getCommentLikes().size();
    }
}

