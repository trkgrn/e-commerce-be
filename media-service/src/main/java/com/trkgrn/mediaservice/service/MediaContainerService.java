package com.trkgrn.mediaservice.service;

import com.trkgrn.mediaservice.model.MediaContainer;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaContainerRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaContainerRequest;
import com.trkgrn.common.dto.mediaservice.response.MediaContainerDto;

import java.util.List;
import java.util.Optional;

public interface MediaContainerService {
    List<MediaContainerDto> findAll();

    Optional<MediaContainer> getEntityById(Long id);

    MediaContainerDto getById(Long id);

    MediaContainerDto create(CreateMediaContainerRequest request);

    MediaContainerDto updateById(Long id, UpdateMediaContainerRequest request);

    void deleteById(Long id);

    void addMediaToContainer(Long containerId, Long mediaId);

    void removeMediaFromContainer(Long containerId, Long mediaId);

    MediaContainerDto buildMediaContainerDto(MediaContainer mediaContainer);
}
