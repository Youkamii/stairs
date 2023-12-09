package com.moon.slopery.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moon.slopery.comment.dto.CommentResponseDto;
import com.moon.slopery.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ZonedDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ZonedDateTime modifiedAt;
    private final List<CommentResponseDto> commentResponseDtoList;

    public PostResponseDto(Post post) {
        this.id = post.getPostId();
        this.writer = post.getUser().getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();

        if (!post.getCreatedAt().equals(post.getModifiedAt())) {
            this.modifiedAt = post.getModifiedAt();
        } else {
            this.createdAt = post.getCreatedAt();
        }

        this.commentResponseDtoList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
    }
}
