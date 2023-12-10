package com.sparta.stairs.user.entity;

import com.sparta.stairs.user.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	@Column
	private String nickname;

	@Column
	private String introduction;

	@Column(nullable = false)
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<UserPasswordHistory> passwordHistoryList = new ArrayList<>();

	public User(String username, String password, String email) {
		this.username = username;
		this.nickname = username;
		this.password = password;
		this.email = email;
		this.role = UserRoleEnum.ROLE_USER;
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

	public void changePassword(String newPassword) {
		this.password = newPassword;
	}


}
