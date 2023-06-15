package rsb.http.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:10:57
 * @since 2022.04.0
 */
@Configuration
public class SimpleFunctionalEndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> customerApis(GreetingsHandlerFunction handler) {// <1>
        // <2>
        return route()
                .GET("/hello/{name}",request -> { // <3>
                    var nameVariable = request.pathVariable("name");
                    var message = String.format("Hello %s",nameVariable);
                    return ServerResponse.ok().bodyValue(message);
                })
                .GET("/hodor",handler) // <4>
                .GET("/sup",handler::handle) // <5>
                .build();

    }
}
