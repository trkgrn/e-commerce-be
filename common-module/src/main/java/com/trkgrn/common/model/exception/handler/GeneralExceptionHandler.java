package com.trkgrn.common.model.exception.handler;

import com.trkgrn.common.constants.ExceptionConstantCode;
import com.trkgrn.common.model.exception.*;
import com.trkgrn.common.model.result.ErrorDataResult;
import com.trkgrn.common.model.result.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ErrorResult> handle(JwtTokenExpiredException exception) {
        return new ResponseEntity<>(new ErrorResult((long) HttpStatus.UNAUTHORIZED.value(), exception.getCause().getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDataResult<Map<String, String>>> handle(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ErrorDataResult<>(errors, "Invalid input"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResult> handle(NotFoundException exception) {
        return new ResponseEntity<>(new ErrorResult(ExceptionConstantCode.NOT_FOUND_EXCEPTION, exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotUpdatedException.class)
    public ResponseEntity<ErrorResult> handle(NotUpdatedException exception) {
        return new ResponseEntity<>(new ErrorResult(ExceptionConstantCode.NOT_UPDATED_EXCEPTION, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotCreatedException.class)
    public ResponseEntity<ErrorResult> handle(NotCreatedException exception) {
        return new ResponseEntity<>(new ErrorResult(ExceptionConstantCode.NOT_CREATED_EXCEPTION, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotDeletedException.class)
    public ResponseEntity<ErrorResult> handle(NotDeletedException exception) {
        return new ResponseEntity<>(new ErrorResult(ExceptionConstantCode.NOT_DELETED_EXCEPTION, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> handle(IllegalArgumentException exception) {
        return new ResponseEntity<>(new ErrorResult(ExceptionConstantCode.ILLEGAL_ARGUMENT, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handle(Exception exception) {
        return new ResponseEntity<>(new ErrorResult((long) HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<ErrorResult> handle(InvalidVerificationCodeException exception) {
        return new ResponseEntity<>(new ErrorResult(ExceptionConstantCode.INVALID_VERIFICATION_CODE, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResult> handle(AlreadyExistsException exception) {
        return new ResponseEntity<>(new ErrorResult(ExceptionConstantCode.ALREADY_EXISTS_EXCEPTION, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ErrorResult> handle(NotValidException exception) {
        return new ResponseEntity<>(new ErrorResult(ExceptionConstantCode.NOT_VALID_EXCEPTION, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
