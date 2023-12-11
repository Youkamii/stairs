package com.sparta.stairs.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @Schema(description = "commentText", example = "댓글 내용")
    @NotBlank
    @Size(max = 40, message = "최대 40자까지 입력")
    private String commentText;
}

