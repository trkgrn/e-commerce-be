package com.trkgrn.productgalleryservice.service;

import com.trkgrn.common.dto.productgalleryservice.request.CreateProductGalleryRequest;
import com.trkgrn.common.dto.productgalleryservice.request.CreateVariantProductGalleryRequest;
import com.trkgrn.common.dto.productgalleryservice.response.ProductGalleryDto;
import com.trkgrn.common.dto.productgalleryservice.response.VariantProductGalleryDto;
import com.trkgrn.productgalleryservice.model.ProductGallery;

import java.util.List;
import java.util.Optional;

public interface ProductGalleryService {
    Optional<ProductGallery> getEntityById(Long id);
    ProductGalleryDto getById(Long id);
    void deleteById(Long id);
    ProductGalleryDto createForBaseProduct(Long baseProductId, CreateProductGalleryRequest request);
    List<ProductGalleryDto> getAllByBaseProductId(Long baseProductId);
    VariantProductGalleryDto createForVariantProduct(Long variantProductId, CreateVariantProductGalleryRequest request);
    List<VariantProductGalleryDto> getAllByVariantProductId(Long variantProductId);
    void deleteByBaseProductId(Long baseProductId);
    void deleteByVariantProductId(Long variantProductId);
    void deleteByMediaContainerId(Long mediaContainerId);
}
