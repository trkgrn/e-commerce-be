package com.trkgrn.productservice.controller;

import com.trkgrn.common.model.result.Result;
import com.trkgrn.common.model.result.SuccessDataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productservice.constants.MessageConstants;
import com.trkgrn.productservice.service.ProductSearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("/filter")
    public ResponseEntity<Result> filterProducts(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(new SuccessDataResult<>(productSearchService.filterProducts(query, getPageable(page, size)),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_FETCHED_SUCCESSFULLY)));
    }

    @GetMapping("/search")
    public ResponseEntity<Result> searchProducts(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam String searchTerm) {
        return ResponseEntity.ok(new SuccessDataResult<>(productSearchService.searchProducts(searchTerm, getPageable(page, size)),
                Localization.getLocalizedMessage(MessageConstants.PRODUCT_FETCHED_SUCCESSFULLY)));
    }


    private Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

}
