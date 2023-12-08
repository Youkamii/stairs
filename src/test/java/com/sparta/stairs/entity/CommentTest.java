package com.sparta.stairs.entity;

import com.sparta.stairs.dto.CommentRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CommentTest {

    @Test
    @DisplayName("댓글 업데이트")
    void updateComment() {
        // given
        CommentRequestDto commentRequestDto = new CommentRequestDto("1", "첫 댓글");

        User user = new User();
        Comment comment = new Comment(commentRequestDto, user);

        // 업데이트 할 댓글 정의
        String newComment = "업데이트한 댓글";

        // when
        CommentRequestDto updatedCommentRequestDto = new CommentRequestDto();
        updatedCommentRequestDto.setComment(newComment);
        comment.update(updatedCommentRequestDto);

        // then : 댓글이 업데이트 됐는지 확인
        assertEquals(newComment, comment.getComment());
    }

    @Test
    void initPost() {
    }

    @Test
    void getId() {
    }

    @Test
    void getComment() {
    }
}