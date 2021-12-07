package com.example.streamingreactive;

import com.example.streamingreactive.model.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StreamingReactiveApplicationTests {
// Spring boot will inject a configured WebTest Client

    @Autowired
    WebTestClient webTestClient;

    @Test
    void fetchQuote()
    {
        webTestClient.get()
                .uri("/quotes?size=20")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Quote.class)
                .hasSize(20)
                .consumeWith(allQuote->{
                   assertThat(allQuote.getResponseBody())
                           .allSatisfy(quote ->
                                  assertThat(quote.getPrice()).isPositive());
                   assertThat(allQuote.getResponseBody()).hasSize(20);
                });
    }

    @Test
    void testStreamQuote() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        webTestClient.get()
                .uri("/quotes")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .returnResult(Quote.class)
                .getResponseBody()
                .take(10)
                .subscribe(quote->{
                   assertThat(quote.getPrice()).isPositive();
                   countDownLatch.countDown();
                });
        countDownLatch.await();
    }


    @Test
    void contextLoads() {
    }

}
