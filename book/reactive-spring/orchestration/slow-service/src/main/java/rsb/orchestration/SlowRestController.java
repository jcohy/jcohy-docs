package rsb.orchestration;

import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:11:01
 * @since 2022.04.0
 */
@RestController
public class SlowRestController {

    private static final Logger log = LoggerFactory.getLogger(SlowRestController.class);

    private final long slowServiceDelay;

    private final AtomicInteger port = new AtomicInteger();

    // <1>
    public SlowRestController(@Value("${rsb.slow-service.delay}") long slowServiceDelay) {
        this.slowServiceDelay = slowServiceDelay;
    }

    // <2>
    @EventListener
    public void web(WebServerInitializedEvent event) {
        port.set(event.getWebServer().getPort());
        if(log.isInfoEnabled()) {
            log.info("configured rsb.slow-service.delay=" + slowServiceDelay + " on port " + port.get());
        }
    }

    // <3>
    Mono<GreetingResponse> greet(@RequestParam(required = false, defaultValue = "world") String name) {
        var now = Instant.now().toString();
        var message = "Hello, %s!  (from %s started at %s and finished at %s)";
        return Mono.just(new GreetingResponse(String.format(message,port,name,now,Instant.now().toString())))
                .doOnNext(r -> log.info(r.toString()))
                .delaySubscription(Duration.ofSeconds(slowServiceDelay));
    }
}
