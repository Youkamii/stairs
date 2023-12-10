package com.sparta.stairs.redis.post.controller;

import com.sparta.stairs.global.CommonResponseDto;
import com.sparta.stairs.redis.post.dto.PostRequestDto;
import com.sparta.stairs.redis.post.dto.PostResponseDto;
import com.sparta.stairs.redis.post.dto.PostUpdateRequestDto;
import com.sparta.stairs.redis.post.service.PostService;
import com.sparta.stairs.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUser());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 게시글 모두 불러오기 ( 관리자 작성글 가장 위로 오도록 )
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> responseDtoList = postService.getPosts();

        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);
    }

    // 게시글 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.getPost(postId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 내가 작성한 게시글
    @GetMapping("/my-post")
    public ResponseEntity<List<PostResponseDto>> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDto> responseDtoList = postService.getMyPosts(userDetails.getUser().getUsername());

        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);
    }

    // 게시글 검색 ( 제목, 내용 )
    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> getPostsByKeyword(@RequestParam String keyword) {
        List<PostResponseDto> responseDtoList = postService.getPostsByKeyword(keyword);

        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);
    }

    // 게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @Valid @RequestBody PostUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.updatePost(postId, requestDto, userDetails.getUser());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());

        return new ResponseEntity<>(new CommonResponseDto("게시글 삭제 완료", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
