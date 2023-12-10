package com.sparta.stairs.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {

	@NotBlank
	@Pattern(regexp ="^[a-zA-Z0-9]{6,20}$", message = "아이디를 확인해주세요.")
	private String username;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*.,]{8,20}$", message = "올바르지 않은 비밀번호 형식입니다.")
	private String password;
}
