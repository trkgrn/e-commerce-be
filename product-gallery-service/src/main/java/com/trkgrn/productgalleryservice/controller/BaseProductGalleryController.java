package com.trkgrn.productgalleryservice.controller;

import com.trkgrn.common.dto.productgalleryservice.request.CreateProductGalleryRequest;
import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productgalleryservice.constants.MessageConstants;
import com.trkgrn.productgalleryservice.service.ProductGalleryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
public class BaseProductGalleryController {

    private final ProductGalleryService productGalleryService;

    public BaseProductGalleryController(ProductGalleryService productGalleryService) {
        this.productGalleryService = productGalleryService;
    }

    @PostMapping("/{id}/galleries")
    public ResponseEntity<Result> createForBaseProduct(@PathVariable Long id, @RequestBody CreateProductGalleryRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(productGalleryService.createForBaseProduct(id, request),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_SAVED_SUCCESSFULLY)));
    }

    @GetMapping("/{id}/galleries")
    public ResponseEntity<Result> getAllByBaseProductId(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(productGalleryService.getAllByBaseProductId(id),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_FETCHED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}/galleries")
    public ResponseEntity<Result> deleteByBaseProductId(@PathVariable Long id) {
        productGalleryService.deleteByBaseProductId(id);
        return ResponseEntity.ok(new SuccessDataResult<>(Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_DELETED_SUCCESSFULLY)));
    }

}
