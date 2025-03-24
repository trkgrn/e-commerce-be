package com.trkgrn.mediaservice.controller;

import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.model.result.SuccessResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.mediaservice.constants.MessageConstants;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaContainerRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaContainerRequest;
import com.trkgrn.mediaservice.service.MediaContainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/media-containers")
public class MediaContainerController {

    private final MediaContainerService mediaContainerService;

    public MediaContainerController(MediaContainerService mediaContainerService) {
        this.mediaContainerService = mediaContainerService;
    }

    @GetMapping
    public ResponseEntity<Result> getAll() {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaContainerService.findAll(),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_FETCHED_SUCCESSFULLY)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaContainerService.getById(id),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_FETCHED_SUCCESSFULLY)));
    }

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody CreateMediaContainerRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaContainerService.create(request),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_SAVED_SUCCESSFULLY)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateById(@PathVariable Long id, @RequestBody UpdateMediaContainerRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(mediaContainerService.updateById(id, request),
                Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_UPDATED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable Long id) {
        mediaContainerService.deleteById(id);
        return ResponseEntity.ok(new SuccessResult(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_DELETED_SUCCESSFULLY)));
    }

    @PostMapping("/{id}/medias")
    public ResponseEntity<Result> addMediaToContainer(@PathVariable Long id, @RequestParam Long mediaId) {
        mediaContainerService.addMediaToContainer(id, mediaId);
        return ResponseEntity.ok(new SuccessResult(Localization.getLocalizedMessage(MessageConstants.MEDIA_ADDED_TO_CONTAINER_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}/medias")
    public ResponseEntity<Result> removeMediaFromContainer(@PathVariable Long id, @RequestParam Long mediaId) {
        mediaContainerService.removeMediaFromContainer(id, mediaId);
        return ResponseEntity.ok(new SuccessResult(Localization.getLocalizedMessage(MessageConstants.MEDIA_REMOVED_FROM_CONTAINER_SUCCESSFULLY)));
    }


}
