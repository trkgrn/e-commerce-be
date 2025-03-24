package com.trkgrn.mediaservice.service;

import com.trkgrn.common.model.enums.FileType;
import com.trkgrn.mediaservice.model.Media;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaRequest;
import com.trkgrn.common.dto.mediaservice.response.MediaDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface MediaService {
    List<MediaDto> findAll();

    Optional<Media> getEntityById(Long id);

    MediaDto getById(Long id);

    MediaDto create(CreateMediaRequest request);

    MediaDto updateById(Long id, UpdateMediaRequest request);

    void deleteById(Long id);

    void updateFile(Long id, MultipartFile file, FileType fileType);

    void deleteFile(Long id);

    MediaDto buildMediaDto(Media media);

    List<MediaDto> buildMediaDtos(List<Media> medias);
}
