package com.trkgrn.mediaservice.controller;

import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.model.result.SuccessResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.common.model.enums.FileType;
import com.trkgrn.mediaservice.constants.MessageConstants;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaRequest;
import com.trkgrn.mediaservice.service.MediaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/medias")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public ResponseEntity<Result> getAll() {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaService.findAll(),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_FETCHED_SUCCESSFULLY)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaService.getById(id),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_FETCHED_SUCCESSFULLY)));
    }

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody CreateMediaRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaService.create(request),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_SAVED_SUCCESSFULLY)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateById(@PathVariable Long id, @RequestBody UpdateMediaRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaService.updateById(id, request),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_UPDATED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable Long id) {
        mediaService.deleteById(id);
        return ResponseEntity.ok(new SuccessResult(Localization.getLocalizedMessage(MessageConstants.MEDIA_DELETED_SUCCESSFULLY)));
    }

    @PutMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Result> updateFile(@PathVariable Long id, @RequestPart("file") MultipartFile file, @RequestParam FileType fileType) {
        mediaService.updateFile(id, file, fileType);
        return ResponseEntity.ok(new SuccessResult(Localization.getLocalizedMessage(MessageConstants.MEDIA_FILE_UPDATED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}/file")
    public ResponseEntity<Result> deleteFile(@PathVariable Long id) {
        mediaService.deleteFile(id);
        return ResponseEntity.ok(new SuccessResult(Localization.getLocalizedMessage(MessageConstants.MEDIA_FILE_DELETED_SUCCESSFULLY)));
    }


}
