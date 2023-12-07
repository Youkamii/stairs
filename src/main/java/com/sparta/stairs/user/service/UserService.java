package com.sparta.stairs.user.service;

import com.sparta.stairs.global.exception.CustomException;
import com.sparta.stairs.global.exception.user.ChangePasswordEqualException;
import com.sparta.stairs.user.dto.ChangePasswordRequestDto;
import com.sparta.stairs.user.dto.ProfileModifyRequestDto;
import com.sparta.stairs.user.dto.ProfileResponseDto;
import com.sparta.stairs.user.dto.SignupRequestDto;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sparta.stairs.global.exception.user.NotFoundUserException;
import com.sparta.stairs.security.UserDetailsimplements;

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


	public void changePassword(ChangePasswordRequestDto requestDto, UserDetailsimplements userDetails) {
		// 예외
		User user = userRepository.findById(userDetails.getUser().getId())
				.orElseThrow(NotFoundUserException::new);
		String newPassword = passwordEncoder.encode(requestDto.getNewPassword());

		// 한번 더 받는 과정

		// 3번 안에 사용한 비밀번호 사용 못하게

		// 기존 == 새 번호 같을 경우
		if (requestDto.getCurrentPassword().equals(requestDto.getNewPassword())) {
			throw new ChangePasswordEqualException();
		}

		//
		user.setPassword(newPassword);
	}

	public void modifyProfile(Long userId, ProfileModifyRequestDto requestDto, UserDetailsimplements userDetails) {
		// 현재 수정 요청하는 ID와 수정하려는 프로필 ID가 동일한지 확인
		if (!userId.equals(userDetails.getUser().getId())) {
			throw new CustomException(HttpStatus.FORBIDDEN, "사용자 ID가 일치하지 않습니다.");
		}

		// 사용자 정보 조회
		User user = userRepository.findById(userId)
				.orElseThrow(NotFoundUserException::new);

		// 이전 닉네임과 동일한 경우 예외 발생
		if (user.getNickname().equals(requestDto.getNickname())) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "새 닉네임은 이전 닉네임과 달라야 합니다.");
		}


		if (requestDto.getNickname() != null && !requestDto.getNickname().isEmpty()) {
			user.setNickname(requestDto.getNickname());
		}
		if (requestDto.getIntroduction() != null) {
			user.setIntroduction(requestDto.getIntroduction());
		}
		if (requestDto.getEmail() != null && !requestDto.getEmail().isEmpty()) {
			user.setEmail(requestDto.getEmail());
		}

		userRepository.save(user);
	}

	// 프로필 조회
	public ProfileResponseDto findProfile(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

		return new ProfileResponseDto(user.getNickname(), user.getIntroduction(), user.getEmail());
	}
}

