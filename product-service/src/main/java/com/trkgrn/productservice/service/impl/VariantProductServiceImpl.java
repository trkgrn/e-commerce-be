package com.trkgrn.productservice.service.impl;

import com.trkgrn.common.dto.productservice.request.CreateVariantProductRequest;
import com.trkgrn.common.dto.productservice.request.UpdateVariantProductRequest;
import com.trkgrn.common.dto.productservice.response.VariantProductDto;
import com.trkgrn.common.model.exception.NotCreatedException;
import com.trkgrn.common.model.exception.NotFoundException;
import com.trkgrn.common.model.exception.NotUpdatedException;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productservice.constants.MessageConstants;
import com.trkgrn.productservice.model.entity.Product;
import com.trkgrn.productservice.model.entity.VariantProduct;
import com.trkgrn.productservice.mapper.VariantProductMapper;
import com.trkgrn.productservice.repository.VariantProductRepository;
import com.trkgrn.productservice.service.ProductService;
import com.trkgrn.productservice.service.VariantProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class VariantProductServiceImpl implements VariantProductService {

    private final Logger log = Logger.getLogger(VariantProductServiceImpl.class.getName());

    private final VariantProductRepository variantProductRepository;
    private final ProductService productService;
    private final VariantProductMapper variantProductMapper;

    public VariantProductServiceImpl(VariantProductRepository variantProductRepository, ProductService productService, VariantProductMapper variantProductMapper) {
        this.variantProductRepository = variantProductRepository;
        this.productService = productService;
        this.variantProductMapper = variantProductMapper;
    }

    @Override
    public VariantProductDto create(CreateVariantProductRequest request) {
        VariantProduct variantProduct = variantProductMapper.toEntity(request);
        Product baseProduct = productService.getEntityById(request.getBaseProductId())
                .orElseThrow(() -> {
                    log.severe("Product not found with id: " + request.getBaseProductId());
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_NOT_FOUND));
                });
        variantProduct.setBaseProduct(baseProduct);
        try {
            variantProduct = variantProductRepository.save(variantProduct);
            return variantProductMapper.toDto(variantProduct);
        } catch (Exception e) {
            log.severe("Variant product not saved: " + e.getMessage());
            throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_NOT_SAVED));
        }
    }

    @Override
    public Optional<VariantProduct> getEntityById(Long id) {
        return variantProductRepository.findById(id);
    }

    @Override
    public VariantProductDto getById(Long id) {
        VariantProduct variantProduct = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe("Variant product not found with id: " + id);
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_NOT_FOUND));
                });
        return variantProductMapper.toDto(variantProduct);
    }

    @Override
    public List<VariantProductDto> getAllWithPagination(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return variantProductRepository.findAll(pageable)
                .stream()
                .map(variantProductMapper::toDto)
                .toList();
    }

    @Override
    public VariantProductDto updateById(Long id, UpdateVariantProductRequest request) {
        VariantProduct variantProduct = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe("Variant product not found with id: " + id);
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_NOT_FOUND));
                });
        variantProduct = variantProductMapper.toEntity(request, variantProduct);
        if (Objects.nonNull(request.getBaseProductId())) {
            Product baseProduct = productService.getEntityById(request.getBaseProductId())
                    .orElseThrow(() -> {
                        log.severe("Base product not found with id: " + request.getBaseProductId());
                        return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_NOT_FOUND));
                    });
            variantProduct.setBaseProduct(baseProduct);
        }
        try {
            variantProduct = variantProductRepository.save(variantProduct);
            return variantProductMapper.toDto(variantProduct);
        } catch (Exception e) {
            log.severe("Variant product not updated: " + e.getMessage());
            throw new NotUpdatedException(Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_NOT_UPDATED));
        }
    }

    @Override
    public void deleteById(Long id) {
        VariantProduct variantProduct = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe("Variant product not found with id: " + id);
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_NOT_FOUND));
                });
        try {
            variantProductRepository.delete(variantProduct);
        } catch (Exception e) {
            log.severe("Variant product not deleted: " + e.getMessage());
            throw new NotFoundException(Localization.getLocalizedMessage(MessageConstants.VARIANT_PRODUCT_NOT_DELETED));
        }
    }

}
