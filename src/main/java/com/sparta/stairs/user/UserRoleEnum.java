package com.sparta.stairs.user;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

	ROLE_ADMIN(Authority.ADMIN),
	ROLE_USER(Authority.USER);

	private final String authority;

	UserRoleEnum(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return this.authority;
	}

	public static class Authority {
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String USER = "ROLE_USER";
	}
}