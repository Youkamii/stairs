package com.sparta.stairs.user.controller;

import com.sparta.stairs.security.UserDetailsimplements;
import com.sparta.stairs.user.dto.ChangePasswordRequestDto;
import com.sparta.stairs.user.dto.ProfileModifyRequestDto;
import com.sparta.stairs.user.dto.ProfileResponseDto;
import com.sparta.stairs.user.dto.SignupRequestDto;
import com.sparta.stairs.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
		userService.signup(requestDto);
		return new ResponseEntity<>("회원가입에 성공하였습니다.", HttpStatus.CREATED);
	}

	// 비밀번호 변경
	@PutMapping("/password")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequestDto requestDto,
												 @AuthenticationPrincipal UserDetailsimplements userDetails) {

		userService.changePassword(requestDto, userDetails);
		return new ResponseEntity<>("비밀번호가 정상적으로 변경되었습니다.", HttpStatus.OK);
	}

	// 프로필 수정
	@PatchMapping("/{userId}/profile")
	public ResponseEntity<String> modifyProfile(@PathVariable Long id,
																  @Valid @RequestBody ProfileModifyRequestDto requestDto,
																  @AuthenticationPrincipal UserDetailsimplements userDetails) {
		userService.modifyProfile(id, requestDto, userDetails);
		return new ResponseEntity<>("프로필이 정상적으로 수정되었습니다.", HttpStatus.OK);
	}

	// 프로필 조회
	@GetMapping("/{userId}/profile")
	public ResponseEntity<ProfileResponseDto> findProfile(@PathVariable Long userId) {
		ProfileResponseDto profile = userService.findProfile(userId);
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}
}
