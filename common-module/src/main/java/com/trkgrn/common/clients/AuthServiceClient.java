package com.trkgrn.common.clients;

import com.trkgrn.common.dto.authservice.response.TokenDto;
import com.trkgrn.common.model.result.DataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", path = "/v1/auth")
public interface AuthServiceClient {
    @PostMapping("/validate")
    ResponseEntity<DataResult<TokenDto>> validateToken(@RequestParam String token);
}
