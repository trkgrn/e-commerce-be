package com.trkgrn.productgalleryservice.controller;

import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productgalleryservice.constants.MessageConstants;
import com.trkgrn.productgalleryservice.service.ProductGalleryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/galleries")
public class ProductGalleryController {

    private final ProductGalleryService productGalleryService;

    public ProductGalleryController(ProductGalleryService productGalleryService) {
        this.productGalleryService = productGalleryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(productGalleryService.getById(id),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_FETCHED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable Long id) {
        productGalleryService.deleteById(id);
        return ResponseEntity.ok(new SuccessDataResult<>(Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_DELETED_SUCCESSFULLY)));
    }

}
