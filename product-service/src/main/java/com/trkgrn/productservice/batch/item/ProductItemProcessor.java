package com.trkgrn.productservice.batch.item;

import com.trkgrn.common.dto.productgalleryservice.response.ProductGalleryDto;
import com.trkgrn.common.dto.productgalleryservice.response.VariantProductGalleryDto;
import com.trkgrn.common.model.result.DataResult;
import com.trkgrn.common.clients.ProductGalleryClient;
import com.trkgrn.productservice.mapper.ProductGalleryIndexMapper;
import com.trkgrn.productservice.mapper.ProductIndexMapper;
import com.trkgrn.productservice.model.entity.VariantProduct;
import com.trkgrn.productservice.model.index.ProductGalleryIndex;
import com.trkgrn.productservice.model.index.ProductIndex;
import com.trkgrn.productservice.model.index.VariantProductGalleryIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component("productItemProcessor")
public class ProductItemProcessor implements ItemProcessor<VariantProduct, ProductIndex> {

    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    private final ProductIndexMapper productIndexMapper;
    private final ProductGalleryIndexMapper productGalleryIndexMapper;
    private final ProductGalleryClient productGalleryClient;

    public ProductItemProcessor(ProductIndexMapper productIndexMapper, ProductGalleryIndexMapper productGalleryIndexMapper, ProductGalleryClient productGalleryClient) {
        this.productIndexMapper = productIndexMapper;
        this.productGalleryIndexMapper = productGalleryIndexMapper;
        this.productGalleryClient = productGalleryClient;
    }

    @Override
    public ProductIndex process(@Nonnull VariantProduct item) {
        ProductIndex productIndex = productIndexMapper.toIndex(item);
        productIndex.setGalleries(getVariantProductGallery(item.getId()));
        if (Objects.nonNull(productIndex.getBaseProduct()) ) {
            productIndex.getBaseProduct().setGalleries(getBaseProductGallery(productIndex.getBaseProduct().getId()));
        }
        log.info("ProductId: {} is processed", item.getId());
        return productIndex;
    }

    private List<ProductGalleryIndex> getBaseProductGallery(Long id) {
        try {
            ResponseEntity<DataResult<List<ProductGalleryDto>>> response = productGalleryClient.getAllByBaseProductId(id);
            if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())) {
                return productGalleryIndexMapper.toGalleryIndex(response.getBody().getData());
            }
        } catch (Exception ignored) {
        }
        return Collections.emptyList();
    }

    private List<VariantProductGalleryIndex> getVariantProductGallery(Long id) {
        try {
            ResponseEntity<DataResult<List<VariantProductGalleryDto>>> response = productGalleryClient.getAllByVariantProductId(id);
            if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())) {
                return productGalleryIndexMapper.toVariantGalleryIndex(response.getBody().getData());
            }
        } catch (Exception ignored) {
        }
        return Collections.emptyList();
    }

}
