package com.trkgrn.authservice.controller;

import com.trkgrn.common.dto.authservice.request.AuthRequest;
import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.authservice.constants.MessageConstants;
import com.trkgrn.common.dto.authservice.request.UserRegisterRequest;
import com.trkgrn.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Result> register(@RequestBody @Valid UserRegisterRequest request) {
        return new ResponseEntity<>(new SuccessDataResult<>(authService.register(request),
                Localization.getLocalizedMessage(MessageConstants.USER_CREATED_SUCCESSFULLY)), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody @Valid AuthRequest request) {
        return new ResponseEntity<>(new SuccessDataResult<>(authService.login(request),
                Localization.getLocalizedMessage(MessageConstants.AUTH_SUCCESSFULLTY)), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Result> refresh(@RequestParam String refreshToken) {
        return new ResponseEntity<>(new SuccessDataResult<>(authService.refresh(refreshToken),
                Localization.getLocalizedMessage(MessageConstants.AUTH_SUCCESSFULLTY)), HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<Result> validate(@RequestParam String token) {
        return new ResponseEntity<>(new SuccessDataResult<>(authService.validate(token),
                Localization.getLocalizedMessage(MessageConstants.AUTH_SUCCESSFULLTY)), HttpStatus.OK);
    }
}
