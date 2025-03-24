package com.trkgrn.productservice.service;

import com.trkgrn.common.dto.productservice.request.CreateProductRequest;
import com.trkgrn.common.dto.productservice.request.UpdateProductRequest;
import com.trkgrn.common.dto.productservice.response.ProductDto;
import com.trkgrn.productservice.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto create(CreateProductRequest request);
    Optional<Product> getEntityById(Long id);
    ProductDto getById(Long id);
    List<ProductDto> getAllWithPagination(int page, int size);
    ProductDto updateById(Long id, UpdateProductRequest request);
    void deleteById(Long id);
}
