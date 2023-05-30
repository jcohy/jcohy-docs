package rsb.testing.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:15:36
 * @since 2022.04.0
 */
@Configuration
public class CustomerWebConfiguration {

    @Bean
    RouterFunction<ServerResponse> routes(CustomerRepository repository) {
        return route(GET("/customers"), // <2>
                request -> ServerResponse.ok().body(repository.findAll(), Customer.class));

    }
}
