package com.trkgrn.userservice.controller;

import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.model.result.SuccessResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.userservice.constants.MessageConstants;
import com.trkgrn.common.dto.userservice.request.UserRegisterRequest;
import com.trkgrn.common.dto.userservice.request.UserUpdateRequest;
import com.trkgrn.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Result> getAll() {
        return new ResponseEntity<>(new SuccessDataResult<>(userService.findAll(),
                Localization.getLocalizedMessage(MessageConstants.USER_FETCHED_SUCCESSFULLY)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getById(@PathVariable Long id) {
        return new ResponseEntity<>(new SuccessDataResult<>(userService.getById(id),
                Localization.getLocalizedMessage(MessageConstants.USER_FETCHED_SUCCESSFULLY)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody @Valid UserRegisterRequest request) {
        return new ResponseEntity<>(new SuccessDataResult<>(userService.create(request),
                Localization.getLocalizedMessage(MessageConstants.USER_CREATED_SUCCESSFULLY)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateById(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest request) {
        return new ResponseEntity<>(new SuccessDataResult<>(userService.updateById(id, request),
                Localization.getLocalizedMessage(MessageConstants.USER_UPDATED_SUCCESSFULLY)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(new SuccessResult(MessageConstants.USER_DELETED_SUCCESSFULLY), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Result> getByUsername(@PathVariable String username) {
        return new ResponseEntity<>(new SuccessDataResult<>(userService.getByUsername(username),
                Localization.getLocalizedMessage(MessageConstants.USER_FETCHED_SUCCESSFULLY)), HttpStatus.OK);
    }

}
