package rsb.http.routes;

import com.jcohy.docs.reactive_spring.chapter7.webflux.utils.IntervalMessageProducer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:11:25
 * @since 2022.04.0
 */
@Component
public class NestedHandler {

    Mono<ServerResponse> sse(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(IntervalMessageProducer.produce(),String.class);
    }

    Mono<ServerResponse> pathVariable(ServerRequest request) {
        return ServerResponse.ok()
                .syncBody(greet(Optional.of(request.pathVariable("pv"))));
    }

    Mono<ServerResponse> noPathVariable(ServerRequest request) {
        return ServerResponse.ok()
                .syncBody(greet(Optional.ofNullable(null)));
    }

    private Map<String,String> greet(Optional<String> name) {
        var finalName = name.orElse("world");
        return Map.of("message",String.format("Hello %s",finalName));
    }
}
