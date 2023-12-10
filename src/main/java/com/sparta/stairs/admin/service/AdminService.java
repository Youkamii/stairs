package com.sparta.stairs.admin.service;

import com.sparta.stairs.common.exception.CustomException;
import com.sparta.stairs.user.UserRoleEnum;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    @Transactional
    public void blockUser(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (findUser.getRole() == UserRoleEnum.ROLE_ADMIN) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "관리자 계정은 정지시킬 수 없습니다.");
        }
        userRepository.delete(findUser);
    }
}
