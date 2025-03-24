package com.trkgrn.mediaservice.mapper;

import com.trkgrn.mediaservice.model.MediaFormat;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaFormatRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaFormatRequest;
import com.trkgrn.common.dto.mediaservice.response.MediaFormatDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaFormatMapper {
    MediaFormat toEntity(MediaFormatDto mediaFormatDto);

    MediaFormatDto toDto(MediaFormat mediaFormat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MediaFormat partialUpdate(MediaFormatDto mediaFormatDto, @MappingTarget MediaFormat mediaFormat);

    MediaFormat toEntity(CreateMediaFormatRequest request);

    MediaFormatDto toDto(UpdateMediaFormatRequest request);
}