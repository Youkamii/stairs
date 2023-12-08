package com.sparta.stairs.post;

import com.sparta.stairs.config.WebSecurityConfig;
import com.sparta.stairs.post.controller.PostController;
import com.sparta.stairs.post.dto.PostRequestDto;
import com.sparta.stairs.post.dto.PostResponseDto;
import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.post.service.PostService;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {PostController.class}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = WebSecurityConfig.class)
})
@MockBean(JpaMetamodelMappingContext.class)
public class PostControllerTest extends TestSetting {

    @MockBean
    private PostService postService;

    @BeforeEach
    public void setup() {
        this.mockUserSetup();

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSecurityFilter()))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("게시글 작성")
    void createPost() throws Exception {
        // given
        PostRequestDto requestDto = new PostRequestDto("제목 테스트", "내용 테스트");
        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(post("/api/posts")
                        .content(postInfo)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .principal(mockPrincipal)
                )
                .andExpect(status().isCreated())
                .andDo(print());
        
    }

    @Test
    @DisplayName("게시글 단 건 조회")
    void getPost() throws Exception {
        // given
        Long postId = 1L;
        PostRequestDto requestDto = new PostRequestDto("제목 테스트", "내용 테스트");
        User user = new User("username", "nickname", "password", UserRoleEnum.USER);
        Post post = new Post(requestDto, user);
        PostResponseDto responseDto = new PostResponseDto(post);

        given(postService.getPost(postId)).willReturn(responseDto);

        // when - then
        mvc.perform(get("/api/posts/{postId}", postId).principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.contents").value(responseDto.getContents()))
                .andExpect(jsonPath("$.username").value(responseDto.getUsername()))
                .andExpect(jsonPath("$.nickname").value(responseDto.getNickname()))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePost() throws Exception {
        // given
        Long postId = 1L;
        PostRequestDto requestDto = new PostRequestDto("제목 수정 테스트", "내용 수정 테스트");
        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(patch("/api/posts/{pstId}", postId)
                        .content(postInfo)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws Exception {
        // given
        Long postId = 1L;

        // when - then
        mvc.perform(delete("/api/posts/{postId}", postId).principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제되었습니다."))
                .andDo(print());

    }
}
