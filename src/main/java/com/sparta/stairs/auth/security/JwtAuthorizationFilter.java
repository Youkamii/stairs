package com.sparta.stairs.auth.security;

import com.sparta.stairs.auth.jwt.JwtUtil;
import com.sparta.stairs.redis.RedisRepository;
import com.sparta.stairs.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisRepository redisRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.resolveToken(request, jwtUtil.ACCESS_TOKEN_HEADER);
        String refreshToken = jwtUtil.resolveToken(request, jwtUtil.REFRESH_TOKEN_HEADER);

        //1. Access Token 존재?
        if(StringUtils.hasText(accessToken) && !redisRepository.hasBlackList(accessToken)) {
            //1-1. Access Token 유효?
            if(jwtUtil.validateToken(accessToken)) {
                Claims info = jwtUtil.getUserInfoFromToken(accessToken);
                String username = info.getSubject();
                setAuthentication(username);
            } else {
                //2. Refresh Token 존재?
                if (Strings.hasText(refreshToken)) {
                    //2-1. Refresh Token 유효 && Redis에 존재?
                    if (jwtUtil.validateToken(refreshToken) && redisRepository.hasRefreshToken(refreshToken)) {
                        //Reissue Access Token && add Header
                        Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
                        String username = info.getSubject();
                        String newToken = jwtUtil.createAccessToken(username, jwtUtil.getUserRole(info));
                        response.setHeader(jwtUtil.ACCESS_TOKEN_HEADER, newToken);
                        response.setHeader(jwtUtil.REFRESH_TOKEN_HEADER, jwtUtil.BEARER_PREFIX + refreshToken);
                        setAuthentication(username);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}