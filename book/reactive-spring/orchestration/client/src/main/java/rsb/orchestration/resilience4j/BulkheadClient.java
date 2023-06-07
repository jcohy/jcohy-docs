package rsb.orchestration.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.reactor.bulkhead.operator.BulkheadOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.UUID;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:12:52
 * @since 2022.04.0
 */
@Profile("bulkhead")
@Component
public class BulkheadClient {

    private static final Logger log = LoggerFactory.getLogger(BulkheadClient.class);

    private final String uid = UUID.randomUUID().toString();

    private final int availableProcessors = Runtime.getRuntime().availableProcessors();;

    private final int maxCalls = availableProcessors /2 ;

    private final WebClient http;

    public BulkheadClient(WebClient http) {
        this.http = http;
    }

    private final Bulkhead bulkhead = Bulkhead.of("greetings-bulkhead", BulkheadConfig
            .custom()
                    .writableStackTraceEnabled(true)
                    .maxConcurrentCalls(this.maxCalls) // <1>
                    .maxWaitDuration(Duration.ofMillis(5))
            .build());

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        log.info("there are " + availableProcessors + " available, therefore there should be " + availableProcessors + " in the default thread pool");
        var immediate = Schedulers.immediate();
        for(var i = 0; i< availableProcessors; i ++) {
            buildRequest(immediate,i).subscribe();
        }
    }

    private Mono<String> buildRequest(Scheduler schedulers, int i) {
        log.info("bulkhead attemp #" + i);
        return GreetingClientUtils
                .getGreetingFor(this.http,this.uid,"ok")
                .transform(BulkheadOperator.of(this.bulkhead))
                .subscribeOn(schedulers)
                .publishOn(schedulers)
                .onErrorResume(throwable -> {
                    log.info("the bulkhead kicked in for request # " + i + ". Received the following exception "
                    + throwable.getClass().getName() + ".");
                    return Mono.empty();
                })
                .onErrorStop();

    }
}
