package com.trkgrn.common.clients;

import com.trkgrn.common.dto.authservice.response.UserDto;
import com.trkgrn.common.dto.authservice.request.UserRegisterRequest;
import com.trkgrn.common.model.result.DataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/v1/users")
public interface UserServiceClient{
    @PostMapping
    ResponseEntity<DataResult<UserDto>> create(@RequestBody UserRegisterRequest request);

    @GetMapping("/username/{username}")
    ResponseEntity<DataResult<UserDto>> findByUsername(@PathVariable String username);
}
