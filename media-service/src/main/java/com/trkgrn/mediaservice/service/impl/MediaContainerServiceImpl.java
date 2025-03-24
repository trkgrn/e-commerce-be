package com.trkgrn.mediaservice.service.impl;

import com.trkgrn.common.model.exception.NotFoundException;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.mediaservice.model.Media;
import com.trkgrn.mediaservice.model.MediaContainer;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaContainerRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaContainerRequest;
import com.trkgrn.common.dto.mediaservice.response.MediaContainerDto;
import com.trkgrn.mediaservice.repository.MediaContainerRepository;
import com.trkgrn.mediaservice.service.MediaContainerService;
import com.trkgrn.mediaservice.service.MediaService;
import com.trkgrn.mediaservice.constants.MessageConstants;
import org.springframework.stereotype.Service;

import com.trkgrn.mediaservice.mapper.MediaContainerMapper;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MediaContainerServiceImpl implements MediaContainerService {

    private final Logger log = Logger.getLogger(MediaContainerServiceImpl.class.getName());

    private final MediaContainerRepository mediaRepository;
    private final MediaContainerMapper mediaContainerMapper;
    private final MediaService mediaService;

    public MediaContainerServiceImpl(MediaContainerRepository mediaRepository, MediaContainerMapper mediaContainerMapper, MediaService mediaService) {
        this.mediaRepository = mediaRepository;
        this.mediaContainerMapper = mediaContainerMapper;
        this.mediaService = mediaService;
    }

    @Override
    public List<MediaContainerDto> findAll() {
        return mediaRepository.findAll()
                .stream()
                .map(this::buildMediaContainerDto)
                .toList();
    }

    @Override
    public Optional<MediaContainer> getEntityById(Long id) {
        return mediaRepository.findById(id);
    }

    @Override
    public MediaContainerDto getById(Long id) {
        return getEntityById(id)
                .map(this::buildMediaContainerDto)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_FOUND));
                });
    }

    @Override
    public MediaContainerDto create(CreateMediaContainerRequest request) {
        MediaContainer media = mediaContainerMapper.toEntity(request);
        try {
            media = mediaRepository.save(media);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_SAVED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_SAVED));
        }
        return mediaContainerMapper.toDto(media);
    }

    @Override
    public MediaContainerDto updateById(Long id, UpdateMediaContainerRequest request) {
        MediaContainer media = getMediaContainer(id);
        try {
            media = mediaContainerMapper.partialUpdate(mediaContainerMapper.toDto(request), media);
            media = mediaRepository.save(media);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_SAVED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_SAVED));
        }
        return mediaContainerMapper.toDto(media);
    }

    @Override
    public void deleteById(Long id) {
        MediaContainer media = getMediaContainer(id);
        try {
            mediaRepository.delete(media);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_DELETED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_DELETED));
        }
    }

    @Override
    public void addMediaToContainer(Long containerId, Long mediaId) {
        MediaContainer mediaContainer = getMediaContainer(containerId);
        Media media = getMedia(mediaId);
        try {
            mediaContainer.getMedias().add(media);
            mediaRepository.save(mediaContainer);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_ADDED_TO_CONTAINER, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_ADDED_TO_CONTAINER));
        }
    }

    @Override
    public void removeMediaFromContainer(Long containerId, Long mediaId) {
        MediaContainer mediaContainer = getMediaContainer(containerId);
        Media media = getMedia(mediaId);
        try {
            mediaContainer.getMedias().remove(media);
            mediaRepository.save(mediaContainer);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_REMOVED_FROM_CONTAINER, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_REMOVED_FROM_CONTAINER));
        }
    }

    @Override
    public MediaContainerDto buildMediaContainerDto(MediaContainer mediaContainer) {
        MediaContainerDto mediaContainerDto = mediaContainerMapper.toDto(mediaContainer);
        if (!CollectionUtils.isEmpty(mediaContainer.getMedias())) {
            mediaContainerDto.setMedias(mediaService.buildMediaDtos(mediaContainer.getMedias()));
        }
        return mediaContainerDto;
    }

    private MediaContainer getMediaContainer(Long containerId) {
        return getEntityById(containerId)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_CONTAINER_NOT_FOUND));
                });
    }

    private Media getMedia(Long mediaId) {
        return mediaService.getEntityById(mediaId)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_NOT_FOUND));
                });
    }

}
