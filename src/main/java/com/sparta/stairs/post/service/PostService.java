package com.sparta.stairs.post.service;

import com.sparta.stairs.global.CustomException;
import com.sparta.stairs.post.dto.PostCreateResponseDto;
import com.sparta.stairs.post.dto.PostRequestDto;
import com.sparta.stairs.post.dto.PostResponseDto;
import com.sparta.stairs.post.dto.PostUpdateResponseDto;
import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.post.repository.PostRepository;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 글 작성
    public PostCreateResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(new Post(requestDto, user));
        post.setUser(user);


        return new PostCreateResponseDto(post);
    }

    
    // 글 전체 조회 * ADMIN 유저가 먼저 반환
    public List<PostResponseDto> getPosts() {
        List<Post> AllPosts = postRepository.findAll();

        List<Post> adminPosts = AllPosts.stream().filter(post -> post.getUser().getRole()
                == UserRoleEnum.ADMIN).collect(Collectors.toList());

        List<Post> userPosts = AllPosts.stream().filter(post -> post.getUser().getRole()
                != UserRoleEnum.ADMIN).collect(Collectors.toList());

        adminPosts.addAll(userPosts);

        return adminPosts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    // 글 단건 조회
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));

        return new PostResponseDto(post);
    }

    // 글 수정
    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (!post.getUser().getNickname().equals(user.getNickname()) && !user.getRole().equals(UserRoleEnum.ADMIN)) { // nickname 으로 수정
            throw new CustomException(HttpStatus.BAD_REQUEST, "작성자 또는 관리자만 수정할 수 있습니다.");
        }

        post.update(requestDto);
        return new PostUpdateResponseDto(post);
    }


    // 글 삭제
    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new CustomException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (!post.getUser().getNickname().equals(user.getNickname()) && !user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "작성자 또는 관리자만 수정할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}
