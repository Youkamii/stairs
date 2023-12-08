package com.sparta.stairs.service;

import com.sparta.stairs.dto.CommentRequestDto;
import com.sparta.stairs.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @Transactional
    public void createComment(Long postId, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(commentRequestDto);
        Post post = findPost(postId);
        post.addComment(comment);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = findComment(commentId);
        checkUsername(comment, user);
        comment.update(commentRequestDto);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);
        checkUsername(comment ,user);
        commentRepository.delete(comment);
    }


0
    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }

    private void checkUsername(Comment comment, User user) {
        String username = user.getUsername();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
    }
}