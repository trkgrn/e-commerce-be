package com.trkgrn.authservice.service;

import com.trkgrn.common.dto.authservice.request.AuthRequest;
import com.trkgrn.common.dto.authservice.request.UserRegisterRequest;
import com.trkgrn.common.dto.authservice.response.AuthResponse;
import com.trkgrn.common.dto.authservice.response.TokenDto;
import com.trkgrn.common.dto.authservice.response.UserDto;

public interface AuthService {
    UserDto register(UserRegisterRequest request);
    AuthResponse login(AuthRequest request);
    AuthResponse refresh(String refreshToken);
    TokenDto validate(String token);
}
