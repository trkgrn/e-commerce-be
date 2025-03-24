package com.trkgrn.productservice.controller;

import com.trkgrn.common.dto.productservice.request.CreateVariantProductRequest;
import com.trkgrn.common.dto.productservice.request.UpdateVariantProductRequest;
import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productservice.constants.MessageConstants;
import com.trkgrn.productservice.service.VariantProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/variant-products")
public class VariantProductController {

    private final VariantProductService variantProductService;

    public VariantProductController(VariantProductService variantProductService) {
        this.variantProductService = variantProductService;
    }

    @GetMapping
    public ResponseEntity<Result> getAll(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new SuccessDataResult<>(variantProductService.getAllWithPagination(page, size),
                Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_FETCHED_SUCCESSFULLY)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(variantProductService.getById(id),
                Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_FETCHED_SUCCESSFULLY)));
    }

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody CreateVariantProductRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(variantProductService.create(request),
                Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_SAVED_SUCCESSFULLY)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateById(@PathVariable Long id, @RequestBody UpdateVariantProductRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(variantProductService.updateById(id, request),
                Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_UPDATED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable Long id) {
        variantProductService.deleteById(id);
        return ResponseEntity.ok(new SuccessDataResult<>(Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_DELETED_SUCCESSFULLY)));
    }


}
