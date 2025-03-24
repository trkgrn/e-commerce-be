package com.trkgrn.productservice.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class ProductIndexingJobScheduler {

    private static final Logger logger = Logger.getLogger(ProductIndexingJobScheduler.class.getName());

    private final JobLauncher jobLauncher;
    private final Job productIndexingJob;

    public ProductIndexingJobScheduler(JobLauncher jobLauncher, Job productIndexingJob) {
        this.jobLauncher = jobLauncher;
        this.productIndexingJob = productIndexingJob;
    }

    @Scheduled(fixedRate = 20, timeUnit = TimeUnit.MINUTES)
    public void run() throws Exception {
        logger.info("Product indexing job started");
        jobLauncher.run(productIndexingJob, getJobParameters());
        logger.info("Product indexing job completed");
    }

    private static JobParameters getJobParameters() {
        return new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
    }

}
