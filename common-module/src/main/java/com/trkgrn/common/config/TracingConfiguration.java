package com.trkgrn.common.config;

import brave.sampler.Sampler;
import com.trkgrn.common.interceptor.TraceInterceptor;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableFeignClients
public class TracingConfiguration implements WebMvcConfigurer {

    @Bean
    public Sampler alwaysSampler() {
        return Sampler.create(1.0f);
    }

    @Bean
    public ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor());
    }
}
