package com.trkgrn.gatewayservice.config;

import com.trkgrn.gatewayservice.decoder.ResponseEntityDecoder;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new JacksonDecoder());
    }
}
