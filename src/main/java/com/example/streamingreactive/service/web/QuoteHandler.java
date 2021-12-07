package com.example.streamingreactive.service.web;

import com.example.streamingreactive.model.Quote;
import com.example.streamingreactive.service.QuoteGeneratorService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
public class QuoteHandler {
private final QuoteGeneratorService service;

public Mono<ServerResponse> fetchQuote(ServerRequest request)
{
    Integer size = Integer.valueOf(request.queryParam("size").orElse("10"));
   return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
           .body(service.fetchQuoteStream(Duration.ofMillis(100l))
            .take(size), Quote.class);
}
public Mono<ServerResponse> streamQuote(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON)
            .body(service.fetchQuoteStream(Duration.ofMillis(100l))
                    , Quote.class);
    }
}
