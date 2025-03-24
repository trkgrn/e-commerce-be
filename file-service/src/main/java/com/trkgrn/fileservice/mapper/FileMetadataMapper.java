package com.trkgrn.fileservice.mapper;

import com.trkgrn.fileservice.model.FileMetadata;
import com.trkgrn.common.dto.fileservice.response.FileMetadataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {
    FileMetadataDto toDto(FileMetadata fileMetadata);
}
