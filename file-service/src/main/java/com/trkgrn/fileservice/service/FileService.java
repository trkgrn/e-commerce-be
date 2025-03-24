package com.trkgrn.fileservice.service;

import com.trkgrn.fileservice.constants.FileType;
import com.trkgrn.common.dto.fileservice.response.FileMetadataDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileMetadataDto saveFile(MultipartFile file, FileType fileType);

    FileMetadataDto getFileMetadataById(String id);

    void deleteFile(String id);
}
