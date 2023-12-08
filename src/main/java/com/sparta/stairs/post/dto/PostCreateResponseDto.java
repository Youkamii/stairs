package com.sparta.stairs.post.dto;

import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.user.dto.CommonResponseDto;
import com.sparta.stairs.user.entity.UserRoleEnum;
import lombok.Getter;
import java.time.ZonedDateTime;

@Getter
public class PostCreateResponseDto extends CommonResponseDto {
    private Long post_id;
    private String username;
    private String title;
    private String contents;
    private UserRoleEnum role;
    private ZonedDateTime createdDateTime;

//    public PostCreateResponseDto(String msg, Integer statusCode) {
//        super(msg, statusCode);
//    }

    public PostCreateResponseDto(Post post) {
        this.post_id = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.role = post.getUser().getRole();
        this.createdDateTime = post.getCreatedDateTime();
    }


}
