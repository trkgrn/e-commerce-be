package com.trkgrn.productservice.service;

import com.trkgrn.common.dto.productservice.response.ProductSearchResponse;
import org.springframework.data.domain.Pageable;

public interface ProductSearchService {
    ProductSearchResponse filterProducts(String query, Pageable pageable);

    ProductSearchResponse searchProducts(String searchTerm, Pageable pageable);
}
