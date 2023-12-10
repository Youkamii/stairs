package com.sparta.stairs.user.dto;

import lombok.Getter;

@Getter
public class ProfileResponseDto {
	private String nickname;
	private String introduction;
	private String email;

	public ProfileResponseDto(String nickname, String introduction, String email) {
		this.nickname = nickname;
		this.introduction = introduction;
		this.email = email;
	}
}