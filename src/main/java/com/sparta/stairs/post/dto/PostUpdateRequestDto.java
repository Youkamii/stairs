package com.sparta.stairs.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostUpdateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private ZonedDateTime modifiedAt = ZonedDateTime.now();
}
