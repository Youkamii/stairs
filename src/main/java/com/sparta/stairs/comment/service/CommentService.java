package com.sparta.stairs.comment.service;

import com.sparta.stairs.comment.dto.CommentRequestDto;
import com.sparta.stairs.comment.dto.CommentResponseDto;
import com.sparta.stairs.comment.dto.CommentUpdateRequestDto;
import com.sparta.stairs.comment.entity.Comment;
import com.sparta.stairs.comment.repository.CommentRepository;
import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto addComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시물을 찾을 수 없습니다."));

        Comment comment = new Comment(requestDto, user, post);
        comment.addCommentToPost(post);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentUpdateRequestDto requestDto, User user) {
        Comment comment = verifyCommentIdAndUser(postId, commentId, user);
        comment.updateComment(requestDto);

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long postId, Long commentId, User user) {
        Comment comment = verifyCommentIdAndUser(postId, commentId, user);

        commentRepository.delete(comment);
    }

    private Comment verifyCommentIdAndUser(Long postId, Long commentId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        if (!user.getUserId().equals(comment.getUser().getUserId())) {
            throw new IllegalArgumentException("해당 게시글 작성자가 아닙니다.");
        }

        return comment;
    }
}
