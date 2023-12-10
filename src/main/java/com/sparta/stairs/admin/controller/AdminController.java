package com.sparta.stairs.admin.controller;

import com.sparta.stairs.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/block/{userId}")
    public ResponseEntity<String> signup(@PathVariable Long userId) {
        adminService.blockUser(userId);
        return new ResponseEntity<>("사용자 계정 정지에 성공하셨습니다.", HttpStatus.CREATED);
    }

}
