package com.trkgrn.fileservice.service.impl;

import com.trkgrn.common.model.exception.NotCreatedException;
import com.trkgrn.common.model.exception.NotDeletedException;
import com.trkgrn.common.model.exception.NotFoundException;
import com.trkgrn.fileservice.constants.FileType;
import com.trkgrn.fileservice.constants.MessageConstants;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.fileservice.model.FileMetadata;
import com.trkgrn.common.dto.fileservice.response.FileMetadataDto;
import com.trkgrn.fileservice.repository.FileRepository;
import com.trkgrn.fileservice.service.FileService;
import com.trkgrn.fileservice.service.StorageService;
import com.trkgrn.fileservice.mapper.FileMetadataMapper;
import com.google.cloud.storage.Blob;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class FileServiceImpl implements FileService {

    private final Logger logger = Logger.getLogger(FileServiceImpl.class.getName());

    private final FileRepository fileRepository;
    private final FileMetadataMapper fileMetadataMapper;
    private final StorageService storageService;

    public FileServiceImpl(FileRepository fileRepository, FileMetadataMapper fileMetadataMapper, StorageService storageService) {
        this.fileRepository = fileRepository;
        this.fileMetadataMapper = fileMetadataMapper;
        this.storageService = storageService;
    }

    @Override
    public FileMetadataDto saveFile(MultipartFile file, FileType fileType) {
        Blob blob = storageService.uploadFile(file, fileType, UUID.randomUUID().toString())
                .orElseThrow(() -> new RuntimeException(Localization.getLocalizedMessage(MessageConstants.STORAGE_FAILED_TO_UPLOAD_FILE)));
        String url = storageService.getDownloadUrl(blob);
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setId(UUID.randomUUID().toString());
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setFileSize(file.getSize());
        fileMetadata.setMimeType(file.getContentType());
        fileMetadata.setUrl(url);
        fileMetadata.setCloudPath(storageService.getFilePathByDownloadUrl(url));
        try {
            fileMetadata = fileRepository.save(fileMetadata);
        } catch (Exception e) {
            storageService.deleteFile(fileMetadata.getCloudPath());
            logger.severe(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_SAVED, Localization.DEFAULT_LOCALE));
            throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_SAVED));
        }
        return fileMetadataMapper.toDto(fileMetadata);
    }

    @Override
    public FileMetadataDto getFileMetadataById(String id) {
        FileMetadata fileMetadata = fileRepository.findById(id)
                .orElseThrow(() -> {
                    logger.severe(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_FOUND));
                });
        return fileMetadataMapper.toDto(fileMetadata);
    }

    @Override
    public void deleteFile(String id) {
        FileMetadata fileMetadata = fileRepository.findById(id)
                .orElseThrow(() -> {
                    logger.severe(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_FOUND));
                });
        if (!storageService.deleteFile(fileMetadata.getCloudPath())) {
            logger.severe(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_DELETED, Localization.DEFAULT_LOCALE));
            throw new NotDeletedException(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_DELETED));
        }
        try {
            fileRepository.delete(fileMetadata);
        } catch (Exception e) {
            logger.severe(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_DELETED, Localization.DEFAULT_LOCALE));
            throw new NotDeletedException(Localization.getLocalizedMessage(MessageConstants.FILE_NOT_DELETED));
        }
    }
}
