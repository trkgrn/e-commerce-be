package com.trkgrn.userservice.service;

import com.trkgrn.userservice.model.User;
import com.trkgrn.common.dto.userservice.request.UserRegisterRequest;
import com.trkgrn.common.dto.userservice.request.UserUpdateRequest;
import com.trkgrn.common.dto.userservice.response.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    Optional<User> getEntityById(Long id);

    UserDto getById(Long id);

    UserDto create(UserRegisterRequest request);

    UserDto updateById(Long id, UserUpdateRequest request);

    void deleteById(Long id);

    UserDto getByUsername(String username);
}
