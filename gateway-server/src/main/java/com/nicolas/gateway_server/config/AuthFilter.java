package com.nicolas.gateway_server.config;

import com.nicolas.gateway_server.dto.RequestDto;
import com.nicolas.gateway_server.dto.TokenDTO;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {


    private WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] hunks = token.split(" ");
            if (hunks.length != 2 || !hunks[0].equals("Bearer")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            return webClientBuilder.build()
                    .post()
                    .uri("http://microservice-auth/auth/validate?token=" + hunks[1])
                    .bodyValue(new RequestDto(exchange.getRequest().getURI().getPath().toString(),
                            exchange.getRequest().getMethod().toString()))

                    .retrieve()
                    .bodyToMono(TokenDTO.class)
                    .flatMap(t -> {
                        t.getToken();
                        return chain.filter(exchange);
                    });
        };
    }


    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete(); // Esto funciona correctamente en WebFlux
    }


    public static class Config {

    }



}
