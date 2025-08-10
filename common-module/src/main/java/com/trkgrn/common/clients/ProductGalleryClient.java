package com.trkgrn.common.clients;

import com.trkgrn.common.dto.productgalleryservice.response.ProductGalleryDto;
import com.trkgrn.common.dto.productgalleryservice.response.VariantProductGalleryDto;
import com.trkgrn.common.model.result.DataResult;
import com.trkgrn.common.model.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-gallery-service", path = "/v1")
public interface ProductGalleryClient {

    @GetMapping("/products/{id}/galleries")
    ResponseEntity<DataResult<List<ProductGalleryDto>>> getAllByBaseProductId(@PathVariable Long id);

    @GetMapping("/variant-products/{id}/galleries")
    ResponseEntity<DataResult<List<VariantProductGalleryDto>>> getAllByVariantProductId(@PathVariable Long id);

    @DeleteMapping("/products/{id}/galleries")
    ResponseEntity<Result> deleteByBaseProductId(@PathVariable Long id);

    @DeleteMapping("/variant-products/{id}/galleries")
    ResponseEntity<Result> deleteByVariantProductId(@PathVariable Long id);

    @DeleteMapping("/galleries/media-container/{mediaContainerId}")
    ResponseEntity<Result> deleteByMediaContainerId(@PathVariable Long mediaContainerId);

}
