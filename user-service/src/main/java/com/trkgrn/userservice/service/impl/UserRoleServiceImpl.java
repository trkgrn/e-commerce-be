package com.trkgrn.userservice.service.impl;

import com.trkgrn.userservice.model.UserRole;
import com.trkgrn.userservice.repository.UserRoleRepository;
import com.trkgrn.userservice.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole getRoleByName(String name) {
        return userRoleRepository.findByName(name);
    }

    @Override
    public UserRole save(UserRole role) {
        return userRoleRepository.save(role);
    }
}
