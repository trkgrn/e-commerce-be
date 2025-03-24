package com.trkgrn.common.model.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class JwtTokenExpiredException extends JwtException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
