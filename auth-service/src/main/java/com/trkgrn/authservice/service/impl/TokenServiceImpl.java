package com.trkgrn.authservice.service.impl;


import com.trkgrn.authservice.model.Token;
import com.trkgrn.common.dto.authservice.response.TokenDto;
import com.trkgrn.authservice.repository.TokenRepository;
import com.trkgrn.authservice.security.model.CustomUserDetails;
import com.trkgrn.authservice.service.TokenService;
import com.trkgrn.common.constants.ApplicationConstants;
import com.trkgrn.common.utils.JwtUtil;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    public TokenServiceImpl(TokenRepository tokenRepository, JwtUtil jwtUtil) {
        this.tokenRepository = tokenRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Token save(Token token, Long expiredTime) {
        return this.tokenRepository.save(token, expiredTime);
    }

    @Override
    public Token findTokenById(String username) {
        return this.tokenRepository.findTokenById(username);

    }

    @Override
    public String delete(String username) {
        return this.tokenRepository.delete(username);
    }

    @Override
    public void storeAccessToken(String userId, String token) {
        tokenRepository.storeAccessToken(userId, token);
    }

    @Override
    public void storeRefreshToken(String userId, String token) {
        tokenRepository.storeRefreshToken(userId, token);
    }

    @Override
    public Optional<String> getAccessToken(String userId) {
        return Optional.ofNullable(tokenRepository.getAccessToken(userId));
    }

    @Override
    public Optional<String> getRefreshToken(String userId) {
        return Optional.ofNullable(tokenRepository.getRefreshToken(userId));
    }

    @Override
    public void deleteAccessToken(String userId) {
        tokenRepository.deleteAccessToken(userId);
    }

    @Override
    public void deleteRefreshToken(String userId) {
        tokenRepository.deleteRefreshToken(userId);
    }

    @Override
    public TokenDto createOrGetTokens(CustomUserDetails user) {
        Optional<String> accessTokenOpt = this.getAccessToken(user.getUsername());
        Optional<String> refreshTokenOpt = this.getRefreshToken(user.getUsername());
        String role = user.getRole().getName();
        if (accessTokenOpt.isPresent() && refreshTokenOpt.isPresent()) {
            return fillTokenDto(accessTokenOpt.get(), refreshTokenOpt.get());
        }
        String accessToken = jwtUtil.generateToken(user.getUsername(), ApplicationConstants.ACCESS_TOKEN, role);
        String refreshToken = jwtUtil.generateToken(user.getUsername(), ApplicationConstants.REFRESH_TOKEN, role);
        this.storeAccessToken(user.getUsername(), accessToken);
        this.storeRefreshToken(user.getUsername(), refreshToken);
        return fillTokenDto(accessToken, refreshToken);
    }

    @Override
    public TokenDto refreshAccessToken(String refreshToken) {
        String username = jwtUtil.extractSubject(refreshToken);
        if (Objects.nonNull(username)) {
            String storedToken = getRefreshToken(username).orElse(null);
            if (Objects.nonNull(storedToken) && storedToken.equals(refreshToken) && !jwtUtil.isTokenExpired(refreshToken)) {
                String role = jwtUtil.extractRole(refreshToken);
                String accessToken = jwtUtil.generateToken(username, ApplicationConstants.ACCESS_TOKEN, role);
                String newRefreshToken = jwtUtil.generateToken(username, ApplicationConstants.REFRESH_TOKEN, role);
                this.storeAccessToken(username, accessToken);
                this.storeRefreshToken(username, newRefreshToken);
                return fillTokenDto(accessToken, newRefreshToken);
            }
        }
        return null;
    }

    @Override
    public Optional<TokenDto> validateToken(String token) {
        if (jwtUtil.isTokenExpired(token)) {
            return Optional.empty();
        }
        String username = jwtUtil.extractSubject(token);
        Optional<String> accessTokenOpt = this.getAccessToken(username);
        Optional<String> refreshTokenOpt = this.getRefreshToken(username);
        if (accessTokenOpt.isPresent() && refreshTokenOpt.isPresent()) {
            return Optional.of(fillTokenDto(accessTokenOpt.get(), refreshTokenOpt.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String extractUsername(String token) {
        return jwtUtil.extractSubject(token);
    }

    @Override
    public String extractRole(String token) {
        return jwtUtil.extractRole(token);
    }

    private TokenDto fillTokenDto(String accessToken, String refreshToken) {
        return new TokenDto(
                accessToken,
                refreshToken,
                jwtUtil.tokenExpiredSeconds(accessToken),
                jwtUtil.tokenExpiredSeconds(refreshToken)
        );
    }

}
