package com.sparta.stairs.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

	@Schema(description = "username", example = "testerid1234123")
	@NotBlank
	@Pattern(regexp ="^[a-zA-Z0-9]{6,20}$", message = "아이디를 확인해주세요.")
	private String username;

	@Schema(description = "password", example = "q1w2e3r4")
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*.,]{8,20}$", message = "올바르지 않은 비밀번호 형식입니다.")
	private String password;

	@Schema(description = "email", example = "tester@test.com")
	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
	private String email;

}
