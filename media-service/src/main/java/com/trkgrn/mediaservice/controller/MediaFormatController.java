package com.trkgrn.mediaservice.controller;

import com.trkgrn.mediaservice.constants.MessageConstants;
import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.model.result.SuccessResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaFormatRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaFormatRequest;
import com.trkgrn.mediaservice.service.MediaFormatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/media-formats")
public class MediaFormatController {

    private final MediaFormatService mediaFormatService;

    public MediaFormatController(MediaFormatService mediaFormatService) {
        this.mediaFormatService = mediaFormatService;
    }

    @GetMapping
    public ResponseEntity<Result> getAll() {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaFormatService.findAll(),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_FETCHED_SUCCESSFULLY)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaFormatService.getById(id),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_FETCHED_SUCCESSFULLY)));
    }

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody CreateMediaFormatRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaFormatService.create(request),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_SAVED_SUCCESSFULLY)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateById(@PathVariable Long id, @RequestBody UpdateMediaFormatRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaFormatService.updateById(id, request),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_UPDATED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable Long id) {
        mediaFormatService.deleteById(id);
        return ResponseEntity.ok(new SuccessResult(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_DELETED_SUCCESSFULLY)));
    }


}
