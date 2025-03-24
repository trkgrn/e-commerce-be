package com.trkgrn.mediaservice.service.impl;

import com.trkgrn.mediaservice.constants.MessageConstants;
import com.trkgrn.common.model.exception.NotFoundException;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.mediaservice.model.MediaFormat;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaFormatRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaFormatRequest;
import com.trkgrn.common.dto.mediaservice.response.MediaFormatDto;
import com.trkgrn.mediaservice.repository.MediaFormatRepository;
import com.trkgrn.mediaservice.service.MediaFormatService;
import com.trkgrn.mediaservice.mapper.MediaFormatMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MediaFormatServiceImpl implements MediaFormatService {

    private final Logger log = Logger.getLogger(MediaFormatServiceImpl.class.getName());

    private final MediaFormatRepository mediaFormatRepository;
    private final MediaFormatMapper mediaFormatMapper;

    public MediaFormatServiceImpl(MediaFormatRepository mediaFormatRepository, MediaFormatMapper mediaFormatMapper) {
        this.mediaFormatRepository = mediaFormatRepository;
        this.mediaFormatMapper = mediaFormatMapper;
    }

    @Override
    public List<MediaFormatDto> findAll() {
        return mediaFormatRepository.findAll()
                .stream()
                .map(mediaFormatMapper::toDto)
                .toList();
    }

    @Override
    public Optional<MediaFormat> getEntityById(Long id) {
        return mediaFormatRepository.findById(id);
    }

    @Override
    public MediaFormatDto getById(Long id) {
        return getEntityById(id)
                .map(mediaFormatMapper::toDto)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_FOUND));
                });
    }

    @Override
    public MediaFormatDto create(CreateMediaFormatRequest request) {
        MediaFormat mediaFormat = mediaFormatMapper.toEntity(request);
        try {
            mediaFormat = mediaFormatRepository.save(mediaFormat);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_SAVED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_SAVED));
        }
        return mediaFormatMapper.toDto(mediaFormat);
    }

    @Override
    public MediaFormatDto updateById(Long id, UpdateMediaFormatRequest request) {
        MediaFormat mediaFormat = getMediaFormat(id);
        try {
            mediaFormat = mediaFormatMapper.partialUpdate(mediaFormatMapper.toDto(request), mediaFormat);
            mediaFormat = mediaFormatRepository.save(mediaFormat);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_UPDATED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_UPDATED));
        }
        return mediaFormatMapper.toDto(mediaFormat);
    }

    @Override
    public void deleteById(Long id) {
        MediaFormat mediaFormat = getMediaFormat(id);
        try {
            mediaFormatRepository.delete(mediaFormat);
        } catch (Exception e) {
            log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_DELETED, Localization.DEFAULT_LOCALE));
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_DELETED));
        }
    }

    private MediaFormat getMediaFormat(Long id) {
        MediaFormat mediaFormat = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_FOUND, Localization.DEFAULT_LOCALE));
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.MEDIA_FORMAT_NOT_FOUND));
                });
        return mediaFormat;
    }
}
