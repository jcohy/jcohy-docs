package rsb.orchestration.resilience4j;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
 * @version 2022.04.0 2023/6/7:15:14
 * @since 2022.04.0
 */
@Profile("cb")
@Component
public class CircuitBreakerClient {

    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerClient.class);

    private final CircuitBreaker circuitBreaker = CircuitBreaker.of("greetings-cb", CircuitBreakerConfig
            .custom()
                    .failureRateThreshold(50) // <1>
                    .recordExceptions(WebClientResponseException.InternalServerError.class) // <2>
                    .slidingWindowSize(5) // <3>
                    .waitDurationInOpenState(Duration.ofMillis(1000))
                    .permittedNumberOfCallsInHalfOpenState(2)
            .build());

    private final WebClient http;

    private final String uid = UUID.randomUUID().toString();

    public CircuitBreakerClient(WebClient http) {
        this.http = http;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        buildRequest()
                .doOnError( ex -> {
                  if( ex instanceof WebClientResponseException.InternalServerError) {
                      log.error("oops! We got a " + ex.getClass().getSimpleName() + " from our network call. " +
                              "This will probably be a problem but we might try again...");
                  }

                  if(ex instanceof CallNotPermittedException) {
                      log.error("no more requests are permitted, now would be a good time to fail fast");
                  }
                })
                .retry(5)
                .subscribe();
    }

    private Mono<String> buildRequest() {
        return GreetingClientUtils.getGreetingFor(this.http,this.uid,"cb")
                .transformDeferred(CircuitBreakerOperator.of(this.circuitBreaker));
    }
}
