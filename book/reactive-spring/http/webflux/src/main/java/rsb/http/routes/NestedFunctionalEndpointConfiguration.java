package rsb.http.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

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
@Configuration
public class NestedFunctionalEndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> nested(NestedHandler handler) {
        // <1>
        var jsonRP = accept(MediaType.APPLICATION_JSON).or(accept(MediaType.APPLICATION_JSON_UTF8));
        var sseRP = accept(MediaType.TEXT_EVENT_STREAM);

        return route() //
                .nest(path("/nested"),builder ->
                    builder.nest(jsonRP,nestedBuilder ->
                       nestedBuilder.GET("/{pv}",handler::pathVariable) // <2>
                               .GET("",handler::noPathVariable) // <3>
                    )
                            .add(route(sseRP,handler::sse)) // <4>
                )
                .build();

    }
}
