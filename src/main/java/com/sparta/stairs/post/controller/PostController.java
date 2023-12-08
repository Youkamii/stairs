package com.sparta.stairs.post.controller;


import com.sparta.stairs.post.dto.*;
import com.sparta.stairs.post.repository.PostRepository;
import com.sparta.stairs.post.service.PostService;
import com.sparta.stairs.security.UserDetailsImpl;
import com.sparta.stairs.user.dto.CommonResponseDto;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 글 작성
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostCreateResponseDto createResponseDto = postService.createPost(requestDto, userDetails.getUser());
        return new ResponseEntity<>(createResponseDto, HttpStatus.CREATED);
    }


    // 글 전체 조회 (admin유저 글이 먼저 보이게)
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> responseDto = postService.getPosts();
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    // 글 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.getPost(postId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    
    // 글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostUpdateResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostUpdateResponseDto updateResponseDto = postService.updatePost(postId, requestDto, userDetails.getUser());
        return new ResponseEntity<>(updateResponseDto, HttpStatus.OK);
    }

    // 글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }





}
