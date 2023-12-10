package com.sparta.stairs.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.stairs.comment.entity.QComment;
import com.sparta.stairs.post.entity.Post;
import com.sparta.stairs.post.entity.QPost;
import com.sparta.stairs.postlike.entity.QPostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomPostRepository {

    private final JPAQueryFactory factory;

    QPost qPost = QPost.post;
    QPostLike qPostLike = QPostLike.postLike;
    QComment qComment = QComment.comment;

    public Optional<Post> findPostFetchJoin(Long id) {
        return Optional.ofNullable(factory
                .selectFrom(qPost)
                .leftJoin(qPost.postLikes, qPostLike)
                .leftJoin(qPost.comments, qComment).fetchJoin()
                .where(qPost.postId.eq(id))
                .fetchOne());
    }
}
