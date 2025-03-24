package com.trkgrn.authservice.service;


import com.trkgrn.authservice.model.Token;
import com.trkgrn.common.dto.authservice.response.TokenDto;
import com.trkgrn.authservice.security.model.CustomUserDetails;

import java.util.Optional;

public interface TokenService {
    Token save(Token token, Long expiredTime);

    Token findTokenById(String username);

    String delete(String username);

    void storeAccessToken(String userId, String token);

    void storeRefreshToken(String userId, String token);

    Optional<String> getAccessToken(String userId);

    Optional<String> getRefreshToken(String userId);

    void deleteAccessToken(String userId);

    void deleteRefreshToken(String userId);

    TokenDto createOrGetTokens(CustomUserDetails user);

    TokenDto refreshAccessToken(String refreshToken);

    Optional<TokenDto> validateToken(String token);

    String extractUsername(String token);

    String extractRole(String token);
}
