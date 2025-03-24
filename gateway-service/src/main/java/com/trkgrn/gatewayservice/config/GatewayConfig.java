package com.trkgrn.gatewayservice.config;

import com.trkgrn.gatewayservice.security.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GatewayConfig {

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/v1/auth/register",
            "/v1/auth/login",
            "/v1/auth/refresh",
            "/v1/auth/validate",
            "/v1/auth/v3/api-docs",
            "/v1/users/v3/api-docs",
            "/v1/products/v3/api-docs",
            "/v1/galleries/v3/api-docs",
            "/v1/files/v3/api-docs",
            "/v1/medias/v3/api-docs"
    );

    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/v1/auth/**").
                        filters(f-> f.filter(filter.apply(getFilterConfig())))
                        .uri("lb://auth-service"))
                .route("user-service", r -> r.path("/v1/users/**")
                        .filters(f-> f.filter(filter.apply(getFilterConfig())))
                        .uri("lb://user-service"))
                .route("product-service",
                        r -> r.path("/v1/products/**", "/v1/variant-products/**")
                        .uri("lb://product-service"))
                .route("product-gallery-service",
                        r -> r.path("/v1/galleries/**", "/v1/products/*/galleries", "/v1/variant-products/*/galleries")
                        .uri("lb://product-gallery-service"))
                .route("file-service", r -> r.path("/v1/files/**")
                        .uri("lb://file-service"))
                .route("media-service", r -> r.path("/v1/medias/**","/v1/media-containers/**")
                        .uri("lb://media-service"))
                .build();
    }

    private static JwtAuthenticationFilter.Config getFilterConfig() {
        return new JwtAuthenticationFilter.Config().setPublicEndpoints(PUBLIC_ENDPOINTS);
    }

}