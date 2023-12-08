package com.sparta.stairs.post;

import com.sparta.stairs.post.dto.PostCreateResponseDto;
import com.sparta.stairs.post.dto.PostRequestDto;
import com.sparta.stairs.post.dto.PostResponseDto;
import com.sparta.stairs.post.dto.PostUpdateResponseDto;
import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.post.repository.PostRepository;
import com.sparta.stairs.post.service.PostService;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    User user;
    Post post;
    PostRequestDto requestDto;

    @BeforeEach
    void setUp() {
        user = new User();
        requestDto = new PostRequestDto("제목 테스트", "내용 테스트");
        post = new Post(requestDto, user);
    }

    @Test
    @DisplayName("게시글 생성")
    void createTodo() {
        // given
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        PostCreateResponseDto responseDto =  postService.createPost(requestDto, user);

        // then
        assertEquals("제목 테스트", responseDto.getTitle());
        assertEquals(responseDto.getContents(), requestDto.getContents());
    }

    @Test
    @DisplayName("게시글 단 건 조회")
    void getIdTodo() {
        // given
        Long postId = 1L;
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        PostResponseDto responseDto =postService.getPost(postId);

        // then
        assertEquals("제목 테스트", responseDto.getTitle());
        assertEquals("내용 테스트", responseDto.getContents());
    }

    @Test
    @DisplayName("게시글 수정")
    void updateTodo() {
        // given
        Long postId = 1L;
        User user = new User("username", "nickname", "password", UserRoleEnum.USER);
        Post post = new Post(requestDto, user);
        PostRequestDto updateTodo = new PostRequestDto("제목수정 테스트", "내용수정 테스트");
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        PostUpdateResponseDto responseDto = postService.updatePost(postId, updateTodo, user);

        // then
        assertEquals(responseDto.getTitle(), updateTodo.getTitle());
        assertEquals(responseDto.getContents(), updateTodo.getContents());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteTodo() {
        // given
        Long postId = 1L;
        User user = new User("username", "nickname", "password", UserRoleEnum.USER);
        Post post = new Post(requestDto, user);
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        postService.deletePost(postId, user);

        // then
        verify(postRepository).delete(any(Post.class));
    }

}
