package com.sparta.stairs.user.controller;

import com.sparta.stairs.security.UserDetailsImpl;
import com.sparta.stairs.user.dto.*;
import com.sparta.stairs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	@Operation(summary = "회원 가입", description = "signup")
	@ApiResponses ( value = {
			@ApiResponse(responseCode = "200", description = "회원 가입 성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
		userService.signup(requestDto);
		return new ResponseEntity<>("회원가입에 성공하였습니다.", HttpStatus.CREATED);
	}

	@Operation(summary = "로그인", description = "login")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그인 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	@PostMapping("/login")
	public void login(@Valid @RequestBody LoginRequestDto requestDto) {}

	// 비밀번호 변경
	@Operation(summary = "비밀번호 변경", description = "changePassword")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	@PatchMapping("/password")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequestDto requestDto,
												 @AuthenticationPrincipal UserDetailsImpl userDetails) {

		userService.changePassword(requestDto, userDetails.getUser());
		return new ResponseEntity<>("비밀번호가 정상적으로 변경되었습니다.", HttpStatus.OK);
	}

	// 프로필 수정
	@Operation(summary = "프로필 수정", description = "modifyProfile")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	@PatchMapping("/{userId}/profile")
	public ResponseEntity<String> modifyProfile(@PathVariable Long userId,
																  @Valid @RequestBody ProfileModifyRequestDto requestDto,
																  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		userService.modifyProfile(userId, requestDto, userDetails);
		return new ResponseEntity<>("프로필이 정상적으로 수정되었습니다.", HttpStatus.OK);
	}

	// 프로필 조회
	@Operation(summary = "프로필 조회", description = "findProfile")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	@GetMapping("/{userId}/profile")
	public ResponseEntity<ProfileResponseDto> findProfile(@PathVariable Long userId) {
		ProfileResponseDto profile = userService.findProfile(userId);
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}

	//로그아웃
	@Operation(summary = "로그아웃", description = "logout")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청")
	})
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken, @RequestHeader(value = "Refresh-Token", required = false) String refreshToken,
										 @AuthenticationPrincipal UserDetailsImpl userDetails) {
		userService.logout(accessToken, refreshToken, userDetails);
		return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
	}
}
