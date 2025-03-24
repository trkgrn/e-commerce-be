package com.trkgrn.productgalleryservice.service.impl;

import com.trkgrn.common.dto.mediaservice.response.MediaContainerDto;
import com.trkgrn.common.dto.productgalleryservice.request.CreateProductGalleryRequest;
import com.trkgrn.common.dto.productgalleryservice.request.CreateVariantProductGalleryRequest;
import com.trkgrn.common.dto.productgalleryservice.response.ProductGalleryDto;
import com.trkgrn.common.dto.productgalleryservice.response.VariantProductGalleryDto;
import com.trkgrn.common.dto.productservice.response.ProductDto;
import com.trkgrn.common.dto.productservice.response.VariantProductDto;
import com.trkgrn.common.model.exception.NotCreatedException;
import com.trkgrn.common.model.exception.NotDeletedException;
import com.trkgrn.common.model.exception.NotFoundException;
import com.trkgrn.common.model.result.DataResult;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.common.clients.MediaContainerServiceClient;
import com.trkgrn.common.clients.ProductServiceClient;
import com.trkgrn.productgalleryservice.constants.MessageConstants;
import com.trkgrn.productgalleryservice.mapper.ProductGalleryMapper;
import com.trkgrn.productgalleryservice.model.ProductGallery;
import com.trkgrn.productgalleryservice.repository.ProductGalleryRepository;
import com.trkgrn.productgalleryservice.service.ProductGalleryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductGalleryServiceImpl implements ProductGalleryService {

    private final Logger log = Logger.getLogger(ProductGalleryServiceImpl.class.getName());

    private final ProductGalleryRepository productGalleryRepository;
    private final ProductGalleryMapper productGalleryMapper;
    private final MediaContainerServiceClient mediaContainerServiceClient;
    private final ProductServiceClient productServiceClient;

    public ProductGalleryServiceImpl(ProductGalleryRepository productGalleryRepository, ProductGalleryMapper productGalleryMapper, MediaContainerServiceClient mediaContainerServiceClient, ProductServiceClient productServiceClient) {
        this.productGalleryRepository = productGalleryRepository;
        this.productGalleryMapper = productGalleryMapper;
        this.mediaContainerServiceClient = mediaContainerServiceClient;
        this.productServiceClient = productServiceClient;
    }


    @Override
    public Optional<ProductGallery> getEntityById(Long id) {
        return productGalleryRepository.findById(id);
    }

    @Override
    public ProductGalleryDto getById(Long id) {
        ProductGallery product = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe("ProductGallery not found with id: " + id);
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_NOT_FOUND));
                });
        return productGalleryMapper.toDto(product);
    }

    @Override
    public void deleteById(Long id) {
        ProductGallery product = getEntityById(id)
                .orElseThrow(() -> {
                    log.severe("ProductGallery not found with id: " + id);
                    return new NotFoundException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_NOT_FOUND));
                });
        try {
            productGalleryRepository.delete(product);
        } catch (Exception e) {
            log.severe("ProductGallery not deleted: " + e.getMessage());
            throw new NotDeletedException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_NOT_DELETED));
        }
    }

    @Override
    public ProductGalleryDto createForBaseProduct(Long baseProductId, CreateProductGalleryRequest request) {
        request.setProductId(baseProductId);
        validate(request);
        ProductGallery productGallery = productGalleryMapper.toEntity(request);
        try {
            productGallery = productGalleryRepository.save(productGallery);
            return productGalleryMapper.toDto(productGallery);
        } catch (Exception e) {
            log.severe("ProductGallery not saved: " + e.getMessage());
            throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_NOT_DELETED));
        }
    }

    private void validate(CreateProductGalleryRequest request) {
        ResponseEntity<DataResult<ProductDto>> productResponse = productServiceClient.getBaseProductById(request.getProductId());
        if (productResponse.getStatusCode().is4xxClientError()) {
            throw new NotFoundException("Product not found with id: " + request.getProductId());
        }
        ResponseEntity<DataResult<MediaContainerDto>> mediaContainerResponse = mediaContainerServiceClient.getById(request.getMediaContainerId());
        if (mediaContainerResponse.getStatusCode().is4xxClientError()) {
            throw new NotFoundException("Media container not found with id: " + request.getMediaContainerId());
        }
    }

    @Override
    public List<ProductGalleryDto> getAllByBaseProductId(Long baseProductId) {
        return productGalleryRepository.findAllByProductId(baseProductId)
                .stream()
                .map(productGallery -> {
                    ProductGalleryDto productGalleryDto = productGalleryMapper.toDto(productGallery);
                    ResponseEntity<DataResult<MediaContainerDto>> mediaContainerResponse = mediaContainerServiceClient.getById(productGallery.getMediaContainerId());
                    if (mediaContainerResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(mediaContainerResponse.getBody())) {
                        productGalleryDto.setMediaContainer(mediaContainerResponse.getBody().getData());
                    }
                    return productGalleryDto;
                })
                .toList();
    }

    @Override
    public VariantProductGalleryDto createForVariantProduct(Long variantProductId, CreateVariantProductGalleryRequest request) {
        request.setVariantProductId(variantProductId);
        validate(request);
        ProductGallery product = productGalleryMapper.toEntity(request);
        try {
            product = productGalleryRepository.save(product);
            return productGalleryMapper.toVariantDto(product);
        } catch (Exception e) {
            log.severe("ProductGallery not saved: " + e.getMessage());
            throw new NotCreatedException(Localization.getLocalizedMessage(MessageConstants.PRODUCT_GALLERY_NOT_DELETED));
        }
    }

    private void validate(CreateVariantProductGalleryRequest request) {
        ResponseEntity<DataResult<VariantProductDto>> variantProductResponse = productServiceClient.getVariantProductById(request.getVariantProductId());
        if (variantProductResponse.getStatusCode().is4xxClientError()) {
            throw new NotFoundException("Variant product not found with id: " + request.getVariantProductId());
        }
        ResponseEntity<DataResult<MediaContainerDto>> mediaContainerResponse = mediaContainerServiceClient.getById(request.getMediaContainerId());
        if (mediaContainerResponse.getStatusCode().is4xxClientError()) {
            throw new NotFoundException("Media container not found with id: " + request.getMediaContainerId());
        }
    }

    @Override
    public List<VariantProductGalleryDto> getAllByVariantProductId(Long variantProductId) {
        return productGalleryRepository.findAllByVariantProductId(variantProductId)
                .stream()
                .map(productGallery -> {
                    VariantProductGalleryDto productGalleryDto = productGalleryMapper.toVariantDto(productGallery);
                    ResponseEntity<DataResult<MediaContainerDto>> mediaContainerResponse = mediaContainerServiceClient.getById(productGallery.getMediaContainerId());
                    if (mediaContainerResponse.getStatusCode().is2xxSuccessful() && Objects.nonNull(mediaContainerResponse.getBody())) {
                        productGalleryDto.setMediaContainer(mediaContainerResponse.getBody().getData());
                    }
                    return productGalleryDto;
                })
                .toList();
    }

}
