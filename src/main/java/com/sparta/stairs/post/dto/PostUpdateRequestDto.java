package com.sparta.stairs.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostUpdateRequestDto {

    @Schema(description = "title", example = "글 제목 수정")
    @NotBlank
    private String title;

    @Schema(description = "content", example = "글 내용 수정")
    @NotBlank
    private String content;

    private ZonedDateTime modifiedAt = ZonedDateTime.now();
}
