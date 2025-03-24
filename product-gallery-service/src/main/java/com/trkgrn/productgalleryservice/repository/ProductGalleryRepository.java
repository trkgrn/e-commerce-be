package com.trkgrn.productgalleryservice.repository;

import com.trkgrn.productgalleryservice.model.ProductGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductGalleryRepository extends JpaRepository<ProductGallery, Long> {
    List<ProductGallery> findAllByProductId(Long productId);
    List<ProductGallery> findAllByVariantProductId(Long variantProductId);
}