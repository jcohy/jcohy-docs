package rsb.orchestration;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/2:16:53
 * @since 2022.04.0
 */

public class ErrorRestController {

    // <1>
    private final AtomicInteger port = new AtomicInteger();

    // <2>
    private final Map<String ,AtomicInteger> clientCounts = new ConcurrentHashMap<>();

    @EventListener
    public void webServerInitializedEventListener(WebServerInitializedEvent event) {
        port.set(event.getWebServer().getPort());
    }

    private int registerClient(String uid) {
        if (null != uid) {
            this.clientCounts.putIfAbsent(uid,new AtomicInteger(0));
            return this.clientCounts.get(uid).incrementAndGet();
        }
        return 1;
    }

    // <3>
    @GetMapping("/ok")
    Mono<Map<String,String>> okEndpoint(@RequestParam(required = false) String uid) {
        var countThusFar = this.registerClient(uid);
        return Mono.just(Map.of("greeting",String.format("greeting attempt %s from port %s",countThusFar,this.port.get())));
    }

    @GetMapping("/retry")
    Mono<Map<String,String>> retryEndpoint(@RequestParam() String uid) {
        var countThusFar = this.registerClient(uid);
        return countThusFar >2 ?
                Mono.just(Map.of("greeting",String.format("greeting attempt %s from port %s",countThusFar,this.port.get())))
                : Mono.error(new IllegalArgumentException());
    }

    @GetMapping("/cb")
    Mono<Map<String,String>> circuitBreakerEndpoint(@RequestParam String uid) {
        registerClient(uid);
        return Mono.error(new IllegalArgumentException());
    }
}
