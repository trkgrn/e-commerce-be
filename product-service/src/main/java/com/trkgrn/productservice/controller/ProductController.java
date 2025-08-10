package com.trkgrn.productservice.controller;

import com.trkgrn.common.dto.productservice.request.CreateProductRequest;
import com.trkgrn.common.dto.productservice.request.UpdateProductRequest;
import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productservice.constants.MessageConstants;
import com.trkgrn.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Result> getAll(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching all products with pagination - page: {}, size: {}", page, size);
        return ResponseEntity.ok(new SuccessDataResult<>(productService.getAllWithPagination(page, size),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_FETCHED_SUCCESSFULLY)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDataResult<>(productService.getById(id),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_FETCHED_SUCCESSFULLY)));
    }

    @PostMapping
    public ResponseEntity<Result> create(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(productService.create(request),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_SAVED_SUCCESSFULLY)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateById(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(new SuccessDataResult<>(productService.updateById(id, request),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_UPDATED_SUCCESSFULLY)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok(new SuccessDataResult<>(Localization.getLocalizedMessage(MessageConstants.PRODUCT_DELETED_SUCCESSFULLY)));
    }


}
