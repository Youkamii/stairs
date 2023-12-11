package com.sparta.stairs.commentlike.controller;

import com.sparta.stairs.commentlike.service.CommentLikeService;
import com.sparta.stairs.common.dto.CommonResponseDto;
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
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{postId}/comments/{commentId}/likes")
    @Operation(summary = "댓글 좋아요 토글", description = "특정 댓글에 대한 좋아요 상태를 토글합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 상태 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "댓글 또는 게시글 찾을 수 없음")
    })
    public ResponseEntity<CommonResponseDto> addCommentLike(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.toggleCommentLike(postId, commentId, userDetails.getUser());
    }
}
