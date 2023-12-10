package com.sparta.stairs.commentlike.controller;

import com.sparta.stairs.commentlike.service.CommentLikeService;
import com.sparta.stairs.global.CommonResponseDto;
import com.sparta.stairs.security.UserDetailsImpl;
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
    public ResponseEntity<CommonResponseDto> addCommentLike(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.toggleCommentLike(postId, commentId, userDetails.getUser());
    }
}
