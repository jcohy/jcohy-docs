package rsb.http.customers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:11:55
 * @since 2022.04.0
 */
@Configuration
public class CustomerApiEndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> customerApis(CustomerHandler handler) {
        return route()
                .nest(path("/fn/customers"),builder -> builder
                        .GET("/{id}",handler::handlerFindCustomerById)
                        .GET("",handler::handlerFindAll)
                        .POST("",handler::handlerCreateCustomer))
                .build();
    }

}
