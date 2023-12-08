package com.sparta.stairs.user.service;

import com.sparta.stairs.global.exception.CustomException;
import com.sparta.stairs.global.exception.user.NotFoundUserException;
import com.sparta.stairs.security.UserDetailsimplements;
import com.sparta.stairs.user.dto.ChangePasswordRequestDto;
import com.sparta.stairs.user.dto.ProfileModifyRequestDto;
import com.sparta.stairs.user.dto.ProfileResponseDto;
import com.sparta.stairs.user.dto.SignupRequestDto;
import com.sparta.stairs.user.entity.User;
import com.sparta.stairs.user.entity.UserPasswordHistory;
import com.sparta.stairs.user.repository.UserPasswordHistoryRepository;
import com.sparta.stairs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserPasswordHistoryRepository userPasswordHistoryRepository;
	private final PasswordEncoder passwordEncoder;

	public void signup(SignupRequestDto requestDto) {
		//check duplication
		String username = requestDto.getUsername();
		Optional<User> findUser = userRepository.findByUsername(username);

		if(findUser.isPresent()) throw new CustomException(HttpStatus.CONFLICT, "중복된 유저 아이디입니다.");

		String password = passwordEncoder.encode(requestDto.getPassword());
		User user = new User(username, password, requestDto.getEmail());
		userRepository.save(user);

		UserPasswordHistory userPasswordHistory = new UserPasswordHistory(user, password);
		userPasswordHistoryRepository.save(userPasswordHistory);
	}

	@Transactional
	public void changePassword(ChangePasswordRequestDto requestDto, User user) {
		//0. 유저 비교
		User findUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

		//1. 히스토리와 비교
		List<UserPasswordHistory> passwords = userPasswordHistoryRepository.findTop3ByUserOrderByCreatedAtDesc(user);

		for (UserPasswordHistory password : passwords) {
			System.out.println(password);
		}

		String newPassword = requestDto.getNewPassword();
		passwords.stream().map(UserPasswordHistory::getBeforePassword)
				.forEach(pwd -> {if (passwordEncoder.matches(newPassword, pwd)) {
						throw new CustomException(HttpStatus.BAD_REQUEST, "최근 3번 안에 사용한 비밀번호는 사용할 수 없습니다.");}});

//		2. 히스토리 저장, 비밀번호 변경
		String encodePassword = passwordEncoder.encode(newPassword);

		UserPasswordHistory userPasswordHistory = new UserPasswordHistory(findUser, encodePassword);
		userPasswordHistoryRepository.save(userPasswordHistory);

		findUser.changePassword(encodePassword);
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

