package com.sparta.stairs.postlike.controller;

import com.sparta.stairs.common.dto.CommonResponseDto;
import com.sparta.stairs.postlike.service.PostLikeService;
import com.sparta.stairs.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/likes")
    @Operation(summary = "게시글 좋아요 토글", description = "특정 게시글에 대한 좋아요 상태를 토글합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 상태 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    public ResponseEntity<CommonResponseDto> togglePostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.addPostLike(postId, userDetails.getUser());
    }
}

