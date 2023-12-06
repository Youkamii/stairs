package com.sparta.stairs.user.controller;

import com.sparta.stairs.user.dto.ChangePasswordRequestDto;
import com.sparta.stairs.user.dto.SignupRequestDto;
import com.sparta.stairs.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
		userService.signup(requestDto);
		return new ResponseEntity<>("회원가입에 성공하였습니다.", HttpStatus.CREATED);
	}

	// 비밀번호 변경
//	@PutMapping("/password")
//	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequestDto requestDto) {
//
//		userService.changePassword
//	}

}
