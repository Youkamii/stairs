package com.sparta.stairs.postlike.service;

import com.sparta.stairs.global.CommonResponseDto;
import com.sparta.stairs.redis.post.repository.PostRepository;
import com.sparta.stairs.postlike.entity.PostLike;
import com.sparta.stairs.postlike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sparta.stairs.redis.post.entity.Post;
import com.sparta.stairs.user.entity.User;


@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;


    public ResponseEntity<CommonResponseDto> addPostLike(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시물은 존재하지 않습니다."));

        if (user.getUsername().equals(post.getUser().getUsername())) {
            throw new IllegalArgumentException("자신이 작성한 게시물에는 좋아요를 누를 수 없습니다.");
        }

        PostLike postLike = postLikeRepository.findByUserAndPost(user, post).orElse(null);

        if (postLike == null) {
            postLikeRepository.save(new PostLike(user, post));

            return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto(postId + "번 게시물 좋아요 등록", HttpStatus.CREATED.value()));
        } else {
            postLikeRepository.delete(postLike);

            return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDto(postId + "번 게시물 좋아요 취소", HttpStatus.OK.value()));
        }
    }
}
