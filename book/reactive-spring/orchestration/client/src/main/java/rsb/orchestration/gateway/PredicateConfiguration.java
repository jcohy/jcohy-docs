package rsb.orchestration.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Mono;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:11:56
 * @since 2022.04.0
 */
@Profile("predicates")
@Configuration
public class PredicateConfiguration {

    @Bean
    RouteLocator predicatesGateway(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(routeSpec -> routeSpec
                        .path("/")
                        .uri("http://httpbin.org/")
                )
                .route(routeSpec -> routeSpec
                        .header("X-RSB")
                        .uri("http://httpbin.org/")
                )
                .route(routeSpec -> routeSpec
                        .query("uid")
                        .uri("http://httpbin.org/")
                )
                .route(routeSpec -> routeSpec
                        .asyncPredicate(serverWebExchange -> Mono.just(Math.random() > .5)).and().path("/test")
                        .uri("http://httpbin.org/")
                )
                .build();
    }
}
