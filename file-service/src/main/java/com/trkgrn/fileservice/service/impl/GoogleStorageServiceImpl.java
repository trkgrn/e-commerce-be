package com.trkgrn.fileservice.service.impl;

import com.trkgrn.fileservice.constants.MessageConstants;
import com.trkgrn.fileservice.util.FileUtil;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.fileservice.service.StorageService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.trkgrn.fileservice.constants.FileType;


import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class GoogleStorageServiceImpl implements StorageService {
    private final Logger logger = Logger.getLogger(GoogleStorageServiceImpl.class.getName());

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Value("${gcp.bucket.url}")
    private String bucketUrl;

    private final Storage storage;
    private final FileUtil fileUtil;

    public GoogleStorageServiceImpl(Storage storage, FileUtil fileUtil) {
        this.storage = storage;
        this.fileUtil = fileUtil;
    }

    @Override
    public Optional<Blob> uploadFile(MultipartFile file, FileType fileType, String fileName) {
        byte[] fileBytes;
        String mimeType;

        try {
            fileBytes = file.getBytes();
            mimeType = fileUtil.getMimeType(Objects.requireNonNull(file.getOriginalFilename()));
        } catch (Exception e) {
            logger.severe(Localization.getLocalizedMessage(MessageConstants.STORAGE_FAILED_TO_UPLOAD_FILE, Localization.DEFAULT_LOCALE));
            throw new IllegalArgumentException(Localization.getLocalizedMessage(MessageConstants.STORAGE_FILETYPE_NOT_SUPPORTED));
        }

        checkFileType(fileType, mimeType);

        try {
            String filePath = fileType.getPath() + "/" + UUID.randomUUID() + "." + mimeType;
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filePath).setContentType(file.getContentType()).build();
            Storage.BlobTargetOption acl = getBlobTargetOption(fileType);
            return Optional.ofNullable(storage.create(blobInfo, fileBytes, acl));
        } catch (Exception e) {
            logger.severe(Localization.getLocalizedMessage(MessageConstants.STORAGE_FAILED_TO_UPLOAD_FILE, Localization.DEFAULT_LOCALE));
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteFile(String filePath) {
        try {
            storage.delete(bucketName, filePath);
            return true;
        } catch (Exception e) {
            logger.severe(Localization.getLocalizedMessage(MessageConstants.STORAGE_FAILED_TO_DELETE_FILE, Localization.DEFAULT_LOCALE));
            return false;
        }
    }

    @Override
    public String getDownloadUrl(Blob blob) {
        return bucketUrl + blob.getName();
    }

    @Override
    public String getFilePathByDownloadUrl(String downloadUrl) {
        return downloadUrl.replace(bucketUrl, "");
    }

    private void checkFileType(FileType fileType, String mimeType) {
        if (Objects.isNull(fileType)) {
            logger.severe(Localization.getLocalizedMessage(MessageConstants.STORAGE_FILETYPE_NOT_SUPPORTED, Localization.DEFAULT_LOCALE));
            throw new IllegalArgumentException(Localization.getLocalizedMessage(MessageConstants.STORAGE_FILETYPE_NOT_SUPPORTED));
        }

        if (fileType.getMimeTypes().stream().noneMatch(mimeType::equals)) {
            logger.severe(Localization.getLocalizedMessage(MessageConstants.STORAGE_FILETYPE_NOT_SUPPORTED, Localization.DEFAULT_LOCALE));
            throw new IllegalArgumentException(Localization.getLocalizedMessage(MessageConstants.STORAGE_FILETYPE_NOT_SUPPORTED));
        }
    }

    private Storage.BlobTargetOption getBlobTargetOption(FileType fileType) {
        return Storage.BlobTargetOption.predefinedAcl(fileType.getAcl());
    }

}
