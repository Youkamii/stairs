package com.sparta.stairs.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class CommentUpdateRequestDto {

    @Schema(description = "commentText", example = "댓글 내용 수정")
    @NotBlank
    private String commentText;

    private ZonedDateTime modifiedAt = ZonedDateTime.now();
}