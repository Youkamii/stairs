package com.sparta.stairs.redis.post.service;

import com.sparta.stairs.global.CustomException;
import com.sparta.stairs.redis.post.dto.PostRequestDto;
import com.sparta.stairs.redis.post.dto.PostResponseDto;
import com.sparta.stairs.redis.post.dto.PostUpdateRequestDto;
import com.sparta.stairs.redis.post.entity.Post;
import com.sparta.stairs.redis.post.repository.PostRepository;
import com.sparta.stairs.user.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.sparta.stairs.user.entity.User;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 작성
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user);
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    // 게시글 단건 조회
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));

        return new PostResponseDto(post);
    }

    // 게시글 모두 불러오기 ( 관리자 작성글 가장 위로 오도록 )
    public List<PostResponseDto> getPosts() {
        List<Post> AllPosts = postRepository.findAll();

        List<Post> adminPosts = AllPosts.stream().filter(post -> post.getUser().getRole()
                == UserRoleEnum.ROLE_ADMIN).collect(Collectors.toList());

        List<Post> userPosts = AllPosts.stream().filter(post -> post.getUser().getRole()
                != UserRoleEnum.ROLE_ADMIN).collect(Collectors.toList());

        adminPosts.addAll(userPosts);

        return adminPosts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }


    // 게시글 검색 ( 제목, 내용 )
    public List<PostResponseDto> getPostsByKeyword(String keyword) {

        if (!StringUtils.hasText(keyword)) {
            throw new NullPointerException("검색어를 입력해 주세요.");
        }

        return postRepository.findAllByTitleContainingOrContentContainingOrderByModifiedAt(keyword, keyword).stream().map(PostResponseDto::new).toList();
    }

    // 내가 작성한 게시글
    public List<PostResponseDto> getMyPosts(String userId) {
        return postRepository.findAllByUser_UsernameOrderByModifiedAt(userId).stream().map(PostResponseDto::new).toList();
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto requestDto, User user) {
        Post post = verifyPostIdAndUser(postId, user);
        post.updatePost(requestDto);

        return new PostResponseDto(post);
    }

    // 게시글 삭제
    public void deletePost(Long postId, User user) {
        Post post = verifyPostIdAndUser(postId, user);
        postRepository.delete(post);
    }

    // 게시물 찾기, 작성자 확인
    private Post verifyPostIdAndUser(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."));

        if (!user.getUsername().equals(post.getUser().getUsername()) && !user.getRole().equals(UserRoleEnum.ROLE_ADMIN)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "작성자 또는 관리자만 수정할 수 있습니다.");
        }

        return post;
    }
}
