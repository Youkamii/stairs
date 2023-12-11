package com.sparta.stairs.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileModifyRequestDto {

	@Schema(description = "username", example = "testerid1234123")
	@NotBlank(message = "닉네임을 입력해 주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "닉네임은 소문자, 대문자, 숫자만 사용 가능하며, 길이는 8~16자여야 합니다.")
	private String nickname;

	@Schema(description = "password", example = "q1w2e3r4")
	@Size(max = 300, message = "자기소개는 최대 300자까지 가능합니다.")
	private String introduction;

	@Schema(description = "email", example = "tester@test.com")
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$", message = "올바르지 않은 이메일 형식입니다.")
	private String email;


}
