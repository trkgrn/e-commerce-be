package com.trkgrn.common.clients;

import com.trkgrn.common.dto.mediaservice.response.MediaContainerDto;
import com.trkgrn.common.model.result.DataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "media-service", path = "/v1/media-containers")
public interface MediaContainerServiceClient {

    @GetMapping("/{id}")
    ResponseEntity<DataResult<MediaContainerDto>> getById(@PathVariable Long id);

}
