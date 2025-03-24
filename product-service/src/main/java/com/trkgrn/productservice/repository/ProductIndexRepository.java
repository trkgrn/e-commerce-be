package com.trkgrn.productservice.repository;

import com.trkgrn.productservice.model.index.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductIndexRepository extends ElasticsearchRepository<ProductIndex, Long> {
}
