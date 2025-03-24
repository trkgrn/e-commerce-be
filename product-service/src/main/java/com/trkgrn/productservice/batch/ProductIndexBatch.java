package com.trkgrn.productservice.batch;

import com.trkgrn.productservice.model.entity.VariantProduct;
import com.trkgrn.productservice.model.index.ProductIndex;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ProductIndexBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public ProductIndexBatch(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job productIndexingJob(Step productIndexingStep) {
        return new JobBuilder("productIndexingJob", jobRepository)
                .start(productIndexingStep)
                .build();
    }

    @Bean
    public Step productIndexingStep(ItemReader<VariantProduct> productItemReader,
                                    ItemProcessor<VariantProduct, ProductIndex> productItemProcessor,
                                    ItemWriter<ProductIndex> productItemWriter) {
        return new StepBuilder("productIndexingStep", jobRepository)
                .<VariantProduct, ProductIndex>chunk(10, transactionManager)
                .reader(productItemReader)
                .processor(productItemProcessor)
                .writer(productItemWriter)
                .build();
    }

}
