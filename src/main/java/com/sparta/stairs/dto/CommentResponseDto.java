package com.sparta.stairs.dto;


import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String username;
    private final String comment;
    private final ZonedDateTime createDateTime;
    private final ZonedDateTime modifiedDateTime;

    public CommentResponseDto (Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser();
        this.comment = comment.getComment();
        this.createDateTime = comment.getCreateDateTime();
        this.modifiedDateTime = comment.getModifiedDateTime();
    }
}
