package com.trkgrn.fileservice.controller;

import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.model.result.SuccessResult;
import com.trkgrn.fileservice.constants.FileType;
import com.trkgrn.fileservice.constants.MessageConstants;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.fileservice.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Result> saveFile(@RequestPart(value = "file") MultipartFile file, @RequestParam FileType fileType) {
        return ResponseEntity.ok(new SuccessDataResult<>(fileService.saveFile(file, fileType), Localization.getLocalizedMessage(MessageConstants.FILE_SAVED_SUCCESSFULLY)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getFileMetadataById(@PathVariable String id) {
        return ResponseEntity.ok(new SuccessDataResult<>(fileService.getFileMetadataById(id), Localization.getLocalizedMessage(MessageConstants.FILE_FETCHED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteFileById(@PathVariable String id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok(new SuccessResult(Localization.getLocalizedMessage(MessageConstants.FILE_DELETED_SUCCESSFULLY)));
    }

}
