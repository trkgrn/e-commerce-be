package com.trkgrn.mediaservice.mapper;

import com.trkgrn.mediaservice.model.Media;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaRequest;
import com.trkgrn.common.dto.mediaservice.response.MediaDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaMapper {
    Media toEntity(MediaDto mediaDto);

    MediaDto toDto(Media media);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Media partialUpdate(MediaDto mediaDto, @MappingTarget Media media);

    Media toEntity(CreateMediaRequest request);

    MediaDto toDto(UpdateMediaRequest request);
}