package com.jcohy.docs.reactive_spring.chapter7.webflux.http.customers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/23:15:49
 * @since 2022.04.0
 */
@Configuration
public class CustomerViewEndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> customerViews(CustomerRepository repository) {
        return route()
                .GET("/fn/customers.php",r -> {
                    var map = Map.of("customers",repository.findAll(), // <1>
                            "type","Functional Reactive"
                    );
                    return ServerResponse.ok().render("customers",map); // <2>
                })
                .build();
    }
}
