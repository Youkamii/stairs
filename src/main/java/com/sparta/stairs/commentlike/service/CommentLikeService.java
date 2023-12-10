package com.sparta.stairs.commentlike.service;

import com.sparta.stairs.comment.repository.CommentRepository;
import com.sparta.stairs.commentlike.entity.CommentLike;
import com.sparta.stairs.commentlike.repository.CommentLikeRepository;
import com.sparta.stairs.global.CommonResponseDto;
import com.sparta.stairs.redis.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sparta.stairs.redis.post.entity.Post;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.comment.entity.Comment;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public ResponseEntity<CommonResponseDto> toggleCommentLike(Long postId, Long commentId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시물은 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글은 존재하지 않습니다."));

        if (user.getUsername().equals(comment.getUser().getUsername())) {
            throw new IllegalArgumentException("자신이 작성한 댓글에는 좋아요를 누를 수 없습니다.");
        }

        CommentLike commentLike = commentLikeRepository.findByUserAndPostAndComment(user, post, comment).orElse(null);

        if (commentLike == null) {
            commentLikeRepository.save(new CommentLike(user, post, comment));

            return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto(postId + "번 댓글 좋아요 등록", HttpStatus.CREATED.value()));
        } else {
            commentLikeRepository.delete(commentLike);

            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto(postId + "번 댓글 좋아요 취소", HttpStatus.OK.value()));
        }
    }
}
