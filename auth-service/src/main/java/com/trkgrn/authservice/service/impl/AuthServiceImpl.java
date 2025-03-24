package com.trkgrn.authservice.service.impl;

import com.trkgrn.common.clients.UserServiceClient;
import com.trkgrn.authservice.constants.MessageConstants;
import com.trkgrn.common.dto.authservice.request.AuthRequest;
import com.trkgrn.common.dto.authservice.request.UserRegisterRequest;
import com.trkgrn.common.dto.authservice.response.AuthResponse;
import com.trkgrn.common.dto.authservice.response.TokenDto;
import com.trkgrn.common.dto.authservice.response.UserDto;
import com.trkgrn.authservice.security.model.CustomUserDetails;
import com.trkgrn.authservice.security.service.CustomUserDetailsService;
import com.trkgrn.authservice.service.AuthService;
import com.trkgrn.authservice.service.TokenService;
import com.trkgrn.common.model.exception.NotCreatedException;
import com.trkgrn.common.model.exception.NotValidException;
import com.trkgrn.common.model.result.DataResult;
import com.trkgrn.common.utils.Localization;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.logging.Logger;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger log = Logger.getLogger(AuthServiceImpl.class.getName());

    private final UserServiceClient userServiceClient;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthServiceImpl(UserServiceClient userServiceClient, CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userServiceClient = userServiceClient;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public UserDto register(UserRegisterRequest request) {
        DataResult<UserDto> result = userServiceClient.create(request).getBody();
        if (Objects.isNull(result)) {
            throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.USER_NOT_CREATED));
        }
        return result.getData();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            log.severe( Localization.getLocalizedMessage(MessageConstants.AUTH_USERNAME_OR_PASSWORD_INCORRECT, Localization.DEFAULT_LOCALE));
            throw new BadCredentialsException(Localization.getLocalizedMessage(MessageConstants.AUTH_USERNAME_OR_PASSWORD_INCORRECT));
        }
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        TokenDto tokens = tokenService.createOrGetTokens(userDetails);
        return new AuthResponse(tokens, userDetails.getRole().getName());
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        TokenDto tokens = tokenService.refreshAccessToken(refreshToken);
        if (Objects.isNull(tokens)) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.AUTH_TOKEN_INVALID, Localization.DEFAULT_LOCALE));
            throw new NotValidException(Localization.getLocalizedMessage(MessageConstants.AUTH_TOKEN_INVALID));
        }
        return new AuthResponse(tokens, tokenService.extractRole(tokens.getAccessToken()));
    }

    @Override
    public TokenDto validate(String token) {
        return tokenService.validateToken(token)
                .orElseThrow(() -> new NotValidException(Localization.getLocalizedMessage(MessageConstants.AUTH_TOKEN_INVALID)));
    }

}
