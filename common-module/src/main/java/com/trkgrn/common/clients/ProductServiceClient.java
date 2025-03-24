package com.trkgrn.common.clients;

import com.trkgrn.common.dto.productservice.response.ProductDto;
import com.trkgrn.common.dto.productservice.response.VariantProductDto;
import com.trkgrn.common.model.result.DataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", path = "/v1")
public interface ProductServiceClient {

    @GetMapping("/products/{id}")
    ResponseEntity<DataResult<ProductDto>> getBaseProductById(@PathVariable Long id);

    @GetMapping("/variant-products/{id}")
    ResponseEntity<DataResult<VariantProductDto>> getVariantProductById(@PathVariable Long id);


}
