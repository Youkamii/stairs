package com.sparta.stairs.entity;

import com.sparta.stairs.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private  Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment (CommentRequestDto commentRequestDto, User user) {
        this.comment = commentRequestDto.getComment();
        this.user = user;
    }

    public void update (CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

    public void initPost (Post post) {
        this.post = post;
    }
}
