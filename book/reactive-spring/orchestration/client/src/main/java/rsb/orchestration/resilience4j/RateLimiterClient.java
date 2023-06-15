package rsb.orchestration.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:15:14
 * @since 2022.04.0
 */
@Component
@Profile("rl")
public class RateLimiterClient {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterClient.class);

    private final String uid = UUID.randomUUID().toString();

    public RateLimiterClient(WebClient http) {
        this.http = http;
    }

    private final WebClient http;

    private final RateLimiter rateLimiter =  RateLimiter.of("greetings-rl",RateLimiterConfig
            .custom()
                    .limitForPeriod(10) // <1>
                    .limitRefreshPeriod(Duration.ofSeconds(1)) // <2>
                    .timeoutDuration(Duration.ofMillis(25)) //
            .build());

    @EventListener(ApplicationReadyEvent.class)
    public void ready() throws InterruptedException {
        var max = 20;
        var cdl = new CountDownLatch(max);
        var result = new AtomicInteger();
        var errors = new AtomicInteger();
        for ( var i = 0; i < max; i++) {
            this.buildRequest(cdl,result,errors,rateLimiter,i).subscribe();
        }

        cdl.await();

        log.info("there were " + errors.get() + " errors");
        log.info("there were " + result.get() + " results");
    }

    private Mono<String> buildRequest(CountDownLatch cdl, AtomicInteger results, AtomicInteger errors,
                                      RateLimiter rateLimiter, int count) {
        return GreetingClientUtils
                .getGreetingFor(this.http,this.uid,"ok")
                .transformDeferred(RateLimiterOperator.of(rateLimiter))
                .doOnError(ex -> {
                    errors.incrementAndGet();
                    log.info("oops! got an error of type " + ex.getClass().getName());
                })
                .doOnNext(reply -> {
                    results.incrementAndGet();
                    log.info("count is " + count + " @ " + Instant.now() + "(" + reply + ")");
                })
                .doOnTerminate(cdl::countDown);
    }
}
