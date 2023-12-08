package com.sparta.stairs.post.dto;

import com.sparta.stairs.post.entity.Post;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime modifiedAt;
    private final List<CommentResponseDto> commentResponseDtoList;

    public PostResponseDto(Post post) {
        this.id = post.getPostId();
        this.writer = post.getUser().getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentResponseDtoList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
    }
}
