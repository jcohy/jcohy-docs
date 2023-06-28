package rsb.http.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Set;

import static com.jcohy.docs.reactive_spring.chapter7.webflux.http.routes.CaseInsensitiveRequestPredicate.i;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:12:07
 * @since 2022.04.0
 */
@Configuration
public class CustomRoutePredicates {

    private final HandlerFunction<ServerResponse> handler =
            request -> ok().bodyValue("Hello," + request.queryParam("name").orElse("world") + "!");

    @Bean
    RouterFunction<ServerResponse> customerRequestPredicates() {
        var aPeculiarRequestPredicate = GET("/test") // <1>
                .and(accept(MediaType.APPLICATION_JSON_UTF8))
                .and((this::isRequestForAValidUid));


        var caseInsensitiveRequestPredicate = i(GET("/greetings/{name}"));

        return route()
                .add(route(aPeculiarRequestPredicate,this.handler))
                .add(route(caseInsensitiveRequestPredicate,this.handler))
                .build();
    }

    private boolean isRequestForAValidUid(ServerRequest request) {
        var goodUids = Set.of("1","2","3");
        return request.queryParam("uid")
                .map(goodUids::contains)
                .orElse(false);
    }
}
