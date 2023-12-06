package com.sparta.stairs.user.service;

import com.sparta.stairs.global.exception.CustomException;
import com.sparta.stairs.global.exception.user.ChangePasswordEqualException;
import com.sparta.stairs.user.dto.ChangePasswordRequestDto;
import com.sparta.stairs.user.dto.SignupRequestDto;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void signup(SignupRequestDto requestDto) {
		//check duplication
		String username = requestDto.getUsername();
		userRepository.findByUsername(username)
				.orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "중복된 유저 아이디입니다."));


		String password = passwordEncoder.encode(requestDto.getPassword());
		User user = new User(username, password, requestDto.getEmail());
		userRepository.save(user);
	}


	public void changePassword(ChangePasswordRequestDto requestDto) {
		// 예외
//		User user = userRepository.findByUsername()

		// 기존 == 새 번호 같을 경우
		if (requestDto.getCurrentPassword().equals(requestDto.getNewPassword())) {
			throw new ChangePasswordEqualException();
		}

		//

	}
}

