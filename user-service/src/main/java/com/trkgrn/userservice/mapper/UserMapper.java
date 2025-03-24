package com.trkgrn.userservice.mapper;

import com.trkgrn.userservice.model.User;
import com.trkgrn.common.dto.userservice.request.UserRegisterRequest;
import com.trkgrn.common.dto.userservice.request.UserUpdateRequest;
import com.trkgrn.common.dto.userservice.response.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserRegisterRequest request);
    User toEntity(UserUpdateRequest request);
}
