package com.sparta.stairs.post.entity;

import com.sparta.stairs.comment.entity.Comment;
import com.sparta.stairs.common.BaseEntity;
import com.sparta.stairs.post.dto.PostRequestDto;
import com.sparta.stairs.post.dto.PostUpdateRequestDto;
import com.sparta.stairs.postlike.entity.PostLike;
import com.sparta.stairs.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @BatchSize(size = 5)
    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

//    @BatchSize(size = 5)
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
    }

    public void updatePost(PostUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.modifiedAt = requestDto.getModifiedAt();
    }
}
