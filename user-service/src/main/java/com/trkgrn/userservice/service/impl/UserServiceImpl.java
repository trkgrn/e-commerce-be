package com.trkgrn.userservice.service.impl;

import com.trkgrn.common.constants.ApplicationConstants;
import com.trkgrn.common.model.exception.NotCreatedException;
import com.trkgrn.common.model.exception.NotFoundException;
import com.trkgrn.common.model.exception.NotUpdatedException;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.userservice.constants.MessageConstants;
import com.trkgrn.userservice.model.User;
import com.trkgrn.userservice.model.UserRole;
import com.trkgrn.common.dto.userservice.request.UserRegisterRequest;
import com.trkgrn.common.dto.userservice.request.UserUpdateRequest;
import com.trkgrn.common.dto.userservice.response.UserDto;
import com.trkgrn.userservice.repository.UserRepository;
import com.trkgrn.userservice.service.UserRoleService;
import com.trkgrn.userservice.service.UserService;
import com.trkgrn.userservice.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getEntityById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDto getById(Long id) {
        return getEntityById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException(Localization.getLocalizedMessage(MessageConstants.USER_NOT_FOUND)));
    }

    @Override
    public UserDto create(UserRegisterRequest request) {
        User user = userMapper.toEntity(request);
        UserRole userRole = userRoleService.getRoleByName(ApplicationConstants.ROLE_USER);
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            log.severe(MessageConstants.USER_NOT_CREATED);
            throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.USER_NOT_CREATED));
        }
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateById(Long id, UserUpdateRequest request) {
        User user = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.USER_NOT_FOUND));
                    return new NotFoundException (Localization.getLocalizedMessage(MessageConstants.USER_NOT_FOUND));
                });
        user = userMapper.toEntity(request);
        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            log.severe(MessageConstants.USER_NOT_UPDATED);
            throw new NotUpdatedException(Localization.getLocalizedMessage(MessageConstants.USER_NOT_UPDATED));
        }
        return userMapper.toDto(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.USER_NOT_FOUND));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.USER_NOT_FOUND));
                });
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            log.severe(MessageConstants.USER_NOT_DELETED);
            throw new NotUpdatedException(Localization.getLocalizedMessage(MessageConstants.USER_NOT_DELETED));
        }
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.USER_NOT_FOUND));
                    return new NotFoundException (Localization.getLocalizedMessage(MessageConstants.USER_NOT_FOUND));
                });
        return userMapper.toDto(user);
    }
}
