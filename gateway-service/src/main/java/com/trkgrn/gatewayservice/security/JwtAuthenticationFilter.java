package com.trkgrn.gatewayservice.security;

import com.trkgrn.common.clients.AuthServiceClient;
import feign.FeignException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (config.getPublicEndpoints().contains(request.getURI().getPath())) {
                return chain.filter(exchange);
            }

            final String token = getToken(request);
            if (Objects.isNull(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            ApplicationContext context = exchange.getApplicationContext();
            AuthServiceClient authServiceClient = context.getBean(AuthServiceClient.class);
            return Mono.fromCallable(() -> {
                        authServiceClient.validateToken(token);
                        return true;
                    })
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(valid -> chain.filter(exchange))
                    .onErrorResume(e -> {
                        if (e instanceof FeignException) {
                            return onError(exchange, HttpStatus.UNAUTHORIZED);
                        }
                        return onError(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                    });
        };
    }

    private static String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(AUTH_HEADER);
        if (Objects.nonNull(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }

    private static Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }


    public static class Config {
        private List<String> publicEndpoints;

        public Config setPublicEndpoints(List<String> publicEndpoints) {
            this.publicEndpoints = publicEndpoints;
            return this;
        }

        public List<String> getPublicEndpoints() {
            return publicEndpoints;
        }
    }
}
