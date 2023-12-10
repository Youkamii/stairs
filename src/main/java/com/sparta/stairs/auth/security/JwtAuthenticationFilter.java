package com.sparta.stairs.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.stairs.auth.jwt.JwtUtil;
import com.sparta.stairs.global.dto.CustomResponseDto;
import com.sparta.stairs.redis.RedisRepository;
import com.sparta.stairs.security.UserDetailsImpl;
import com.sparta.stairs.user.UserRoleEnum;
import com.sparta.stairs.user.dto.LoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

//로그인 필터
@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final RedisRepository redisRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper, RedisRepository redisRepository) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.redisRepository = redisRepository;
        setFilterProcessesUrl("/api/users/login");
    }

    //로그인 시도 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken(username, role);

        response.addHeader(jwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(jwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        redisRepository.setRefreshToken(refreshToken.substring(7), username);

        CustomResponseDto commonResponseDto = new CustomResponseDto("로그인에 성공하셨습니다.", HttpStatus.OK);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        CustomResponseDto commonResponseDto = new CustomResponseDto("로그인에 실패하셨습니다.", HttpStatus.UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
    }
}