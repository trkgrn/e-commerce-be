package com.trkgrn.productservice.service;

import com.trkgrn.common.dto.productservice.request.CreateVariantProductRequest;
import com.trkgrn.common.dto.productservice.request.UpdateVariantProductRequest;
import com.trkgrn.common.dto.productservice.response.VariantProductDto;
import com.trkgrn.productservice.model.entity.VariantProduct;

import java.util.List;
import java.util.Optional;

public interface VariantProductService {
    VariantProductDto create(CreateVariantProductRequest request);
    Optional<VariantProduct> getEntityById(Long id);
    VariantProductDto getById(Long id);
    List<VariantProductDto> getAllWithPagination(int page, int size);
    VariantProductDto updateById(Long id, UpdateVariantProductRequest request);
    void deleteById(Long id);
}
