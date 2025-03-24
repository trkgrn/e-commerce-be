package com.trkgrn.userservice.service;


import com.trkgrn.userservice.model.UserRole;

public interface UserRoleService {
    UserRole getRoleByName(String name);
    UserRole save(UserRole role);
}
