package com.sparta.stairs.post.dto;

import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class PostResponseDto {
    private Long post_id;
    private String username;
    private String title;
    private String contents;
    private UserRoleEnum role;
    private String nickname;

     private List<CommentResponseDto> comments = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.post_id = post.getId();
        this.username = post.getUser().getUsername();
        this.nickname = getNickname();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.role = post.getUser().getRole();

        for (Comment comment : post.getComments()) {
            this.comments.add(new CommentResponseDto(comment));
        }
    }
}
