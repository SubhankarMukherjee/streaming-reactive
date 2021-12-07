package com.example.streamingreactive.service;

import com.example.streamingreactive.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;


class QuoteGeneratorServiceImplTest {
QuoteGeneratorService service;

    @BeforeEach
    void setUp() {
        service= new QuoteGeneratorServiceImpl();
    }

    @Test
    void fetchQuoteStream() throws InterruptedException {

        Flux<Quote> quoteFlux= service.fetchQuoteStream(Duration.ofMillis(2000));

        Consumer<Quote> quoteConsumer =System.out::println;
        Consumer<Throwable> throwableConsumer = e-> System.out.println(e.getMessage());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable done= ()->countDownLatch.countDown();

        quoteFlux.take(5)
                .subscribe(quoteConsumer,throwableConsumer,done);
        countDownLatch.await();

    }
}