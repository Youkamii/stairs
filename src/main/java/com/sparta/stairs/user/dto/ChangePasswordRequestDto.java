package com.sparta.stairs.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDto {

	@Schema(description = "currentPassword", example = "q1w2e3r4")
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*.,]{8,20}$", message = "올바르지 않은 비밀번호 형식입니다.")
	private String currentPassword;

	@Schema(description = "newPassword", example = "12345678a")
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*.,]{8,20}$", message = "올바르지 않은 비밀번호 형식입니다.")
	private String newPassword;
}
