package com.sparta.stairs.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class CommentUpdateRequestDto {

    @NotBlank
    private String commentText;

    private ZonedDateTime modifiedAt = ZonedDateTime.now();
}