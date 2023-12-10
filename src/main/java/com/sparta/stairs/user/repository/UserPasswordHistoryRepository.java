package com.sparta.stairs.user.repository;

import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.entity.UserPasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPasswordHistoryRepository extends JpaRepository<UserPasswordHistory, Long> {
    List<UserPasswordHistory> findByUserOrderByCreatedAtDesc(User user);
}
