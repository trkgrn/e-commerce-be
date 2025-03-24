package com.trkgrn.productgalleryservice.mapper;

import com.trkgrn.common.dto.productgalleryservice.request.CreateProductGalleryRequest;
import com.trkgrn.common.dto.productgalleryservice.request.CreateVariantProductGalleryRequest;
import com.trkgrn.common.dto.productgalleryservice.response.ProductGalleryDto;
import com.trkgrn.common.dto.productgalleryservice.response.VariantProductGalleryDto;
import com.trkgrn.productgalleryservice.model.ProductGallery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductGalleryMapper {
    ProductGallery toEntity(CreateProductGalleryRequest request);
    ProductGallery toEntity(CreateVariantProductGalleryRequest request);

    ProductGalleryDto toDto(ProductGallery entity);
    VariantProductGalleryDto toVariantDto(ProductGallery entity);
}
