package com.trkgrn.productgalleryservice.controller;

import com.trkgrn.common.dto.productgalleryservice.request.CreateVariantProductGalleryRequest;
import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productgalleryservice.constants.MessageConstants;
import com.trkgrn.productgalleryservice.service.ProductGalleryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/variant-products")
public class VariantProductGalleryController {

    private final ProductGalleryService productGalleryService;

    public VariantProductGalleryController(ProductGalleryService productGalleryService) {
        this.productGalleryService = productGalleryService;
    }

    @PostMapping("/{id}/galleries")
    public ResponseEntity<Result> createForVariantProduct(@PathVariable Long id, @RequestBody CreateVariantProductGalleryRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(productGalleryService.createForVariantProduct(id, request),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_SAVED_SUCCESSFULLY)));
    }

    @GetMapping("/{id}/galleries")
    public ResponseEntity<Result> getAllByVariantProductId(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(productGalleryService.getAllByVariantProductId(id),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_FETCHED_SUCCESSFULLY)));
    }

}
