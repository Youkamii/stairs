package com.sparta.stairs.post.controller;

import com.sparta.stairs.common.dto.CommonResponseDto;
import com.sparta.stairs.post.dto.PostRequestDto;
import com.sparta.stairs.post.dto.PostResponseDto;
import com.sparta.stairs.post.dto.PostUpdateRequestDto;
import com.sparta.stairs.post.service.PostService;
import com.sparta.stairs.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "포스트 작성", description = "새로운 포스트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "포스트 작성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUser());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 게시글 모두 불러오기 ( 관리자 작성글 가장 위로 오도록 )
    @Operation(summary = "게시글 전체 조회", description = "모든 게시글을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> responseDtoList = postService.getPosts();

        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);
    }

    // 게시글 단건 조회
    @Operation(summary = "게시글 단건 조회", description = "특정 게시글을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.getPost(postId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 내가 작성한 게시글
    @Operation(summary = "내 게시글 조회", description = "사용자가 작성한 게시글을 조회합니다.")
    @GetMapping("/my-post")
    public ResponseEntity<List<PostResponseDto>> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDto> responseDtoList = postService.getMyPosts(userDetails.getUser().getUsername());

        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);
    }

    // 게시글 검색 ( 제목, 내용 )
    @Operation(summary = "게시글 검색", description = "제목 또는 내용으로 게시글을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> getPostsByKeyword(@RequestParam String keyword) {
        List<PostResponseDto> responseDtoList = postService.getPostsByKeyword(keyword);

        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);
    }

    // 게시글 수정
    @Operation(summary = "게시글 수정", description = "특정 게시글을 수정합니다.")
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @Valid @RequestBody PostUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.updatePost(postId, requestDto, userDetails.getUser());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());

        return new ResponseEntity<>(new CommonResponseDto("게시글 삭제 완료", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
