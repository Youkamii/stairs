package com.sparta.stairs.user.entity;

import com.sparta.stairs.user.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private UserRoleEnum role;

	@Column
	private String nickname;

	@Column
	private String introduction;

	@Column
	private String email;

	public User(String username, String password, String email) {
		this.username = username;
		this.nickname = username;
		this.password = password;
		this.email = email;
		this.role = UserRoleEnum.USER;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
