package com.trkgrn.mediaservice.service.impl;

import com.trkgrn.common.model.exception.NotCreatedException;
import com.trkgrn.common.model.exception.NotFoundException;
import com.trkgrn.common.model.exception.NotUpdatedException;
import com.trkgrn.common.model.result.DataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.common.clients.FileServiceClient;
import com.trkgrn.common.model.enums.FileType;
import com.trkgrn.mediaservice.model.Media;
import com.trkgrn.mediaservice.model.MediaFormat;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaRequest;
import com.trkgrn.common.dto.mediaservice.response.FileMetadataDto;
import com.trkgrn.common.dto.mediaservice.response.MediaDto;
import com.trkgrn.mediaservice.repository.MediaRepository;
import com.trkgrn.mediaservice.service.MediaFormatService;
import com.trkgrn.mediaservice.service.MediaService;
import com.trkgrn.mediaservice.constants.MessageConstants;
import com.trkgrn.mediaservice.mapper.MediaMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MediaServiceImpl implements MediaService {

    private final Logger log = Logger.getLogger(MediaServiceImpl.class.getName());

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final MediaFormatService mediaFormatService;
    private final FileServiceClient fileServiceClient;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper, MediaFormatService mediaFormatService, FileServiceClient fileServiceClient) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
        this.mediaFormatService = mediaFormatService;
        this.fileServiceClient = fileServiceClient;
    }


    @Override
    public List<MediaDto> findAll() {
        return mediaRepository.findAll()
                .stream()
                .map((this::buildMediaDto))
                .toList();
    }

    private FileMetadataDto getFile(Media media) {
        try {
            if (StringUtils.isNotBlank(media.getFileId())) {
                ResponseEntity<DataResult<FileMetadataDto>> response = fileServiceClient.getFileMetadataById(media.getFileId());
                if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())) {
                    return response.getBody().getData();
                }
            }
        } catch (Exception e) {
            log.severe("File not fetched for media id: " + media.getId() + " and file id: " + media.getFileId());
        }
        return null;
    }


    @Override
    public Optional<Media> getEntityById(Long id) {
        return mediaRepository.findById(id);
    }

    @Override
    public MediaDto getById(Long id) {
        Media media = getMedia(id);
        return buildMediaDto(media);
    }

    @Override
    public MediaDto create(CreateMediaRequest request) {
        Media media = mediaMapper.toEntity(request);
        MediaFormat format = getFormat(request);
        media.setFormat(format);
        try {
            media = mediaRepository.save(media);
            return mediaMapper.toDto(media);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_SAVED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_SAVED));
        }
    }

    private MediaFormat getFormat(CreateMediaRequest request) {
        return mediaFormatService.getEntityById(request.getMediaFormatId())
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_FOUND));
                });
    }

    @Override
    public MediaDto updateById(Long id, UpdateMediaRequest request) {
        Media media = getMedia(id);
        try {
            media = mediaMapper.partialUpdate(mediaMapper.toDto(request), media);
            media = mediaRepository.save(media);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_UPDATED, Localization.DEFAULT_LOCALE));
            throw new NotUpdatedException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_UPDATED));
        }
        return mediaMapper.toDto(media);
    }

    @Override
    public void deleteById(Long id) {
        Media media = getMedia(id);
        try {
            fileServiceClient.deleteFileById(media.getFileId());
            mediaRepository.delete(media);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_DELETED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_DELETED));
        }
    }

    @Override
    public void updateFile(Long id, MultipartFile file, FileType fileType) {
        Media media = getMedia(id);
        FileMetadataDto savedFileMetadata = saveFileToCloudStorage(file, fileType);
        try {
            String oldFileId = media.getFileId();
            media.setFileId(savedFileMetadata.getId());
            mediaRepository.save(media);
            removeOldFile(oldFileId, media);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_FILE_NOT_UPDATED, Localization.DEFAULT_LOCALE));
            throw new NotUpdatedException(Localization.getLocalizedMessage(MessageConstants.MEDIA_FILE_NOT_UPDATED));
        }
    }

    private FileMetadataDto saveFileToCloudStorage(MultipartFile file, FileType fileType) {
        try {
            ResponseEntity<DataResult<FileMetadataDto>> saveFileResponse = fileServiceClient.saveFile(file, fileType);
            if (saveFileResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(saveFileResponse.getBody())) {
                return saveFileResponse.getBody().getData();
            } else if (saveFileResponse.getStatusCode().isError() && Objects.nonNull(saveFileResponse.getBody())) {
                log.severe(saveFileResponse.getBody().getMessage());
                throw new NotCreatedException(saveFileResponse.getBody().getMessage());
            } else {
                log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_SAVED, Localization.DEFAULT_LOCALE));
                throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_SAVED));
            }
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_SAVED, Localization.DEFAULT_LOCALE));
            throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_SAVED));
        }
    }

    @Override
    public void deleteFile(Long id) {
        Media media = getMedia(id);
        try {
            media.setFileId(null);
            mediaRepository.save(media);
            removeOldFile(media.getFileId(), media);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_FILE_NOT_DELETED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_FILE_NOT_DELETED));
        }
    }

    @Override
    public MediaDto buildMediaDto(Media media) {
        MediaDto mediaDto = mediaMapper.toDto(media);
        mediaDto.setFile(getFile(media));
        return mediaDto;
    }

    @Override
    public List<MediaDto> buildMediaDtos(List<Media> medias) {
        if (!CollectionUtils.isEmpty(medias)) {
            return medias.stream()
                    .map(this::buildMediaDto)
                    .toList();
        }
        return List.of();
    }

    private Media getMedia(Long id) {
        return getEntityById(id)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_FOUND));
                });
    }

    private void removeOldFile(String oldFileId, Media media) {
        try {
            if (Objects.nonNull(oldFileId)) {
                fileServiceClient.deleteFileById(media.getFileId());
            }
        } catch (Exception e) {
            log.severe("Old file not deleted for media id: " + media.getId() + " and file id: " + oldFileId);
        }
    }
}
