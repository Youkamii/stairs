package com.sparta.stairs.post;

import com.sparta.stairs.config.JpaConfig;
import com.sparta.stairs.post.dto.PostRequestDto;
import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.post.repository.PostRepository;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.entity.UserRoleEnum;
import com.sparta.stairs.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
public class PostRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("username", "nickname", "password", UserRoleEnum.USER);
        userRepository.save(user);
    }


    @Test
    @DisplayName("내림차순 조회")
    void findAllByOrderByCreatedDateTimeDesc() {
        // given
        userRepository.save(user);
        PostRequestDto requestDto = new PostRequestDto("제목 테스트", "내용 테스트");
        Post post1 = new Post(requestDto, user);
        Post post2 = new Post(requestDto, user);

        postRepository.saveAll(List.of(post1, post2));

        // when
        List<Post> postList = postRepository.findAllByOrderByCreatedDateTimeDesc();

        // then
        assertThat(postList).hasSize(2);
        assertThat(postList.get(0).getTitle()).isEqualTo("제목 테스트");
    }

    @Test
    @DisplayName("제목으로 조회")
    void findTodoByTitleContaining() {
        // given
        userRepository.save(user);
        PostRequestDto requestDto = new PostRequestDto("제목", "내용 테스트");
        Post post1 = new Post(requestDto, user);
        Post post2 = new Post(requestDto, user);
        Post post3 = new Post(requestDto, user);
        postRepository.saveAll(List.of(post1, post2, post3));

        // when
        List<Post> postList = postRepository.findByTitleOrderByCreatedDateTimeDesc("제목");

        // then
        assertThat(postList).containsExactly(post1, post2, post3);

    }

}
