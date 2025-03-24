package com.trkgrn.productservice.service.impl;

import com.trkgrn.common.dto.productservice.request.CreateProductRequest;
import com.trkgrn.common.dto.productservice.request.UpdateProductRequest;
import com.trkgrn.common.dto.productservice.response.ProductDto;
import com.trkgrn.common.model.enums.ApprovalStatus;
import com.trkgrn.common.model.exception.NotCreatedException;
import com.trkgrn.common.model.exception.NotDeletedException;
import com.trkgrn.common.model.exception.NotFoundException;
import com.trkgrn.common.model.exception.NotUpdatedException;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productservice.constants.MessageConstants;
import com.trkgrn.productservice.model.entity.Product;
import com.trkgrn.productservice.mapper.ProductMapper;
import com.trkgrn.productservice.repository.ProductRepository;
import com.trkgrn.productservice.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger log = Logger.getLogger(ProductServiceImpl.class.getName());

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ProductDto create(CreateProductRequest request) {
        Product product = productMapper.toEntity(request);
        try {
            product = productRepository.save(product);
            return productMapper.toDto(product);
        } catch (Exception e) {
            log.severe("Product not saved: " + e.getMessage());
            throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_NOT_SAVED));
        }
    }

    @Override
    public Optional<Product> getEntityById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe("Product not found with id: " + id);
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_NOT_FOUND));
                });
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllWithPagination(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return productRepository.findAll(pageable)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto updateById(Long id, UpdateProductRequest request) {
        Product product = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe("Product not found with id: " + id);
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_NOT_FOUND));
                });
        try {
            product = productMapper.updateEntity(request, product);
            product = productRepository.save(product);
            return productMapper.toDto(product);
        } catch (Exception e) {
            log.severe("Product not updated: " + e.getMessage());
            throw new NotUpdatedException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_NOT_UPDATED));
        }
    }

    @Override
    public void deleteById(Long id) {
        Product product = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe("Product not found with id: " + id);
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_NOT_FOUND));
                });
        try {
            productRepository.delete(product);
        } catch (Exception e) {
            log.severe("Product not deleted: " + e.getMessage());
            throw new NotDeletedException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_NOT_DELETED));
        }
    }


}
