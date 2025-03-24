package com.trkgrn.authservice.security.service;


import com.trkgrn.common.clients.UserServiceClient;
import com.trkgrn.common.dto.authservice.response.UserDto;
import com.trkgrn.authservice.security.model.CustomUserDetails;
import com.trkgrn.common.model.result.DataResult;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;

    public CustomUserDetailsService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DataResult<UserDto> user = userServiceClient.findByUsername(username).getBody();
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new CustomUserDetails(user.getData());
    }

}
