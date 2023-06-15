package rsb.orchestration.resilience4j;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:15:15
 * @since 2022.04.0
 */
@Profile("retry")
@Component
public class RetryClient {

    private static final Logger log = LoggerFactory.getLogger(RetryClient.class);

    private final Retry retry = Retry.of("greetings-retry", RetryConfig
            .custom()
                    .waitDuration(Duration.ofMillis(1000)) // <1>
                    .intervalFunction(IntervalFunction.ofExponentialBackoff(Duration.ofMillis(500L), 2d)) // <2>
                    .maxAttempts(3)
            .build());

    private final String uid = UUID.randomUUID().toString();

    private final WebClient http;

    public RetryClient(WebClient http) {
        this.http = http;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        Mono<String> retry = GreetingClientUtils
                .getGreetingFor(this.http,this.uid,"retry")
                .transformDeferred(RetryOperator.of(this.retry)); // <4>
        retry.subscribe(log::info);
    }
}
