package com.sparta.stairs.post;

import com.sparta.stairs.post.dto.PostRequestDto;
import com.sparta.stairs.post.entity.Post;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {

    Post post = new Post();

    @Test
    void postTest() {
        // when
        post.setTitle("제목 테스트");
        post.setContents("내용 테스트");

        // then
        assertEquals("제목 테스트", post.getTitle());
        assertEquals("내용 테스트", post.getContents());
    }

    @Test
    void postUpdateTest() {
        // given
        PostRequestDto requestDto = new PostRequestDto("제목 테스트", "내용 테스트");

        // when
        post.update(requestDto);

        assertEquals("제목 테스트", post.getTitle());
        assertEquals("내용 테스트", post.getContents());
    }
}
