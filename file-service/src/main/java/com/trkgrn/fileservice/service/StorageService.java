package com.trkgrn.fileservice.service;

import com.trkgrn.fileservice.constants.FileType;
import com.google.cloud.storage.Blob;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface StorageService {
    Optional<Blob> uploadFile(MultipartFile file, FileType fileType, String fileName);

    boolean deleteFile(String filePath);

    String getDownloadUrl(Blob blob);

    String getFilePathByDownloadUrl(String downloadUrl);
}
