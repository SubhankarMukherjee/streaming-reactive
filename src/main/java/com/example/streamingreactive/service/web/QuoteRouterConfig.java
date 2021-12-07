package com.example.streamingreactive.service.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuoteRouterConfig {

    public static final String QUOTES = "/quotes";

    @Bean
    RouterFunction<ServerResponse> getRouterConfig(QuoteHandler handler)
    {
        return route().GET(QUOTES, accept(MediaType.APPLICATION_JSON),handler::fetchQuote)
                .GET(QUOTES,accept(MediaType.APPLICATION_NDJSON),handler::streamQuote)
                .build();
    }
}
