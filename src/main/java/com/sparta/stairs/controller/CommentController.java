package com.sparta.stairs.controller;

import com.sparta.stairs.dto.CommentRequestDto;
import com.sparta.stairs.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity createComment (@PathVariable Long postId,
                                         @RequestBody CommentRequestDto commentRequestDto) {
        commentService.createComment(postId, commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long postId,
                                        @PathVariable Long commentId,
                                        @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(commentId, commentRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long postId,
                                        @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}