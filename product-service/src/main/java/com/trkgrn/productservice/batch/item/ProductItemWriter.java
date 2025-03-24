package com.trkgrn.productservice.batch.item;

import com.trkgrn.productservice.model.index.ProductIndex;
import com.trkgrn.productservice.repository.ProductIndexRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component("productItemWriter")
public class ProductItemWriter implements ItemWriter<ProductIndex> {

    private static final Logger log = LoggerFactory.getLogger(ProductItemWriter.class);

    private final ProductIndexRepository productIndexRepository;

    public ProductItemWriter(ProductIndexRepository productIndexRepository) {
        this.productIndexRepository = productIndexRepository;
    }

    @Override
    public void write(@Nonnull Chunk<? extends ProductIndex> chunk) throws Exception {
        RepositoryItemWriter<ProductIndex> writer = new RepositoryItemWriter<>();
        writer.setRepository(productIndexRepository);
        writer.write(chunk);
        log.info("Chunk is written");
    }
}
