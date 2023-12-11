package com.sparta.stairs.admin.controller;

import com.sparta.stairs.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "사용자 계정 정지", description = "특정 사용자의 계정을 정지합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사용자 계정 정지 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity<String> signup(@PathVariable Long userId) {
        adminService.blockUser(userId);
        return new ResponseEntity<>("사용자 계정 정지에 성공하셨습니다.", HttpStatus.CREATED);
    }

}
