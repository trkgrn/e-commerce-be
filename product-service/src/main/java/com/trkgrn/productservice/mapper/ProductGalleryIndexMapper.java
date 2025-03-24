package com.trkgrn.productservice.mapper;

import com.trkgrn.common.dto.mediaservice.response.MediaContainerDto;
import com.trkgrn.common.dto.mediaservice.response.MediaDto;
import com.trkgrn.common.dto.productgalleryservice.response.ProductGalleryDto;
import com.trkgrn.common.dto.productgalleryservice.response.VariantProductGalleryDto;
import com.trkgrn.productservice.model.index.MediaContainerIndex;
import com.trkgrn.productservice.model.index.MediaIndex;
import com.trkgrn.productservice.model.index.ProductGalleryIndex;
import com.trkgrn.productservice.model.index.VariantProductGalleryIndex;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductGalleryIndexMapper {

    MediaContainerIndex toMediaContainerIndex(MediaContainerDto source);

    @Mappings(value = {
            @Mapping(source = "file.url", target = "url"),
            @Mapping(source = "format.code", target = "format"),
            @Mapping(source = "file.mimeType", target = "mimeType")
    })
    MediaIndex toMediaIndex(MediaDto source);

    @Mapping(source = "mediaContainer", target = "mediaContainer")
    VariantProductGalleryIndex toVariantGalleryIndex(VariantProductGalleryDto source);

    @Mapping(source = "mediaContainer", target = "mediaContainer")
    ProductGalleryIndex toGalleryIndex(ProductGalleryDto source);

    List<VariantProductGalleryIndex> toVariantGalleryIndex(List<VariantProductGalleryDto> galleries);

    List<ProductGalleryIndex> toGalleryIndex(List<ProductGalleryDto> galleries);
}
