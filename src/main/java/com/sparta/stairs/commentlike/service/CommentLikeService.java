package com.sparta.stairs.commentlike.service;

import com.sparta.stairs.commentlike.entity.CommentLike;
import com.sparta.stairs.commentlike.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ResponseEntity<CommonResponseDto> toggleCommentLike(Long postId, Long commentId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시물은 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글은 존재하지 않습니다."));

        if (user.getUserId().equals(comment.getUser().getUserId())) {
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
