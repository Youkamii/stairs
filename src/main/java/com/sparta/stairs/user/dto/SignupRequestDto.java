package com.sparta.stairs.user.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.parameters.P;

@Getter
@Setter
public class SignupRequestDto {

	@NotBlank
	@Pattern(regexp ="^[a-zA-Z0-9]{6,20}$", message = "아이디를 확인해주세요.")
	private String username;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*.,]{8,20}$", message = "올바르지 않은 비밀번호 형식입니다.")
	private String password;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$", message = "올바르지 않은 이메일 형식입니다.")
	private String email;

}
