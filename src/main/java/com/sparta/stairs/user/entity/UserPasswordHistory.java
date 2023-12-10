package com.sparta.stairs.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class UserPasswordHistory {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "user_password_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String beforePassword;

    private LocalDateTime createdAt;

    protected UserPasswordHistory() {}

    public UserPasswordHistory(User user, String beforePassword) {
        this.user = user;
        this.beforePassword = beforePassword;
        this.createdAt = LocalDateTime.now();
    }
}
