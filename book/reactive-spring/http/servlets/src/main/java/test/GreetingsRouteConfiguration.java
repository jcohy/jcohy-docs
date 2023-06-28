package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
 * @version 2022.04.0 2023/5/24:15:00
 * @since 2022.04.0
 */
@Configuration
public class GreetingsRouteConfiguration {
    @Bean
    RouterFunction<ServerResponse> routes() {
        return route()
                .GET("/hello/functional/{name}",request -> {
                    var reply = Greetings.greet("functional", request.pathVariable("name"));
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(reply,Greetings.class);
                })
                .build();
    }
}