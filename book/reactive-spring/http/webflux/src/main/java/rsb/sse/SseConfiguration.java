package rsb.sse;

import com.jcohy.docs.reactive_spring.chapter7.webflux.utils.IntervalMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:15:38
 * @since 2022.04.0
 */
@Configuration
public class SseConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SseConfiguration.class);

    private final String countPathVariable = "count";

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route()
                .GET("/sse/{" + this.countPathVariable + "}",this::handleSse)
                .build();
    }

    Mono<ServerResponse> handleSse(ServerRequest request) {
        var countPathVariable = Integer.parseInt(request.pathVariable(this.countPathVariable));
        var publisher = IntervalMessageProducer.produce(countPathVariable).doOnComplete(() -> log.info("completed"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM) // <1>
                .body(publisher,String.class);
    }
}
