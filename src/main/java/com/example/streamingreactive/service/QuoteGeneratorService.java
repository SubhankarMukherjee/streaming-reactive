package com.example.streamingreactive.service;

import com.example.streamingreactive.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteGeneratorService {


        Flux<Quote> fetchQuoteStream(Duration period);

}
