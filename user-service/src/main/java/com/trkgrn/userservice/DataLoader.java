package com.trkgrn.userservice;

import com.trkgrn.common.constants.ApplicationConstants;
import com.trkgrn.userservice.model.UserRole;
import com.trkgrn.userservice.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRoleService userRoleService;

    public DataLoader(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) {
        UserRole user = userRoleService.getRoleByName(ApplicationConstants.ROLE_USER);
        UserRole admin = userRoleService.getRoleByName(ApplicationConstants.ROLE_ADMIN);
        if (Objects.isNull(user)){
            user = new UserRole(0L, ApplicationConstants.ROLE_USER);
            userRoleService.save(user);
        }
        if (Objects.isNull(admin)){
            admin = new UserRole(0L, ApplicationConstants.ROLE_ADMIN);
            userRoleService.save(admin);
        }
    }
}
