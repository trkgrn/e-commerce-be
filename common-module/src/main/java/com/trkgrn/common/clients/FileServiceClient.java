package com.trkgrn.common.clients;

import com.trkgrn.common.model.result.DataResult;
import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.enums.FileType;
import com.trkgrn.common.dto.mediaservice.response.FileMetadataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service", path = "/v1/files")
public interface FileServiceClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<DataResult<FileMetadataDto>> saveFile(@RequestPart(value = "file") MultipartFile file, @RequestParam FileType fileType);

    @GetMapping("/{id}")
    ResponseEntity<DataResult<FileMetadataDto>> getFileMetadataById(@PathVariable String id);

    @DeleteMapping("/{id}")
    ResponseEntity<Result> deleteFileById(@PathVariable String id);
}
