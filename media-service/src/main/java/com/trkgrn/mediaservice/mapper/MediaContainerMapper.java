package com.trkgrn.mediaservice.mapper;

import com.trkgrn.mediaservice.model.MediaContainer;
import com.trkgrn.common.dto.mediaservice.request.CreateMediaContainerRequest;
import com.trkgrn.common.dto.mediaservice.request.UpdateMediaContainerRequest;
import com.trkgrn.common.dto.mediaservice.response.MediaContainerDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaContainerMapper {
    MediaContainer toEntity(MediaContainerDto mediaContainerDto);

    @Mapping(target = "medias", ignore = true)
    MediaContainerDto toDto(MediaContainer mediaContainer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MediaContainer partialUpdate(MediaContainerDto mediaContainerDto, @MappingTarget MediaContainer mediaContainer);

    MediaContainer toEntity(CreateMediaContainerRequest request);

    MediaContainerDto toDto(UpdateMediaContainerRequest request);
}