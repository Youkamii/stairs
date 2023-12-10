package com.sparta.stairs.comment.entity;

import com.sparta.stairs.comment.dto.CommentRequestDto;
import com.sparta.stairs.comment.dto.CommentUpdateRequestDto;
import com.sparta.stairs.commentlike.entity.CommentLike;
import com.sparta.stairs.global.Time;
import com.sparta.stairs.redis.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.stairs.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.commentText = requestDto.getCommentText();
        this.user = user;
        this.post = post;
    }

    public void addCommentToPost(Post post) {
        post.getComments().add(this);
    };

    public void updateComment(CommentUpdateRequestDto requestDto) {
        this.commentText = requestDto.getCommentText();
        this.modifiedAt = requestDto.getModifiedAt();
    }
}
