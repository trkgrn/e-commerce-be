package com.trkgrn.mediaservice.service;

import com.trkgrn.mediaservice.model.MediaFormat;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaFormatRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaFormatRequest;
import com.trkgrn.common.dto.mediaservice.response.MediaFormatDto;

import java.util.List;
import java.util.Optional;

public interface MediaFormatService {
    List<MediaFormatDto> findAll();

    Optional<MediaFormat> getEntityById(Long id);

    MediaFormatDto getById(Long id);

    MediaFormatDto create(CreateMediaFormatRequest request);

    MediaFormatDto updateById(Long id, UpdateMediaFormatRequest request);

    void deleteById(Long id);
}
