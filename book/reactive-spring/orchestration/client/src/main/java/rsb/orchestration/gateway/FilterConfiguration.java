package rsb.orchestration.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:11:46
 * @since 2022.04.0
 */
@Profile("routes-filters")
@Configuration
public class FilterConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FilterConfiguration.class);

    @Bean
    RouteLocator gateway(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(routeSpec -> routeSpec
                        .path("/")
                        .filters(fs -> fs
                                .setPath("/forms//post") // <1>
                                .retry(10) // <2>
                                .addRequestParameter("uid", UUID.randomUUID().toString()) // <3>
                                .addResponseHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*") // <4>
                                .filters(((exchange, chain) -> { // <5>
                                    var uri = exchange.getRequest().getURI();
                                    return chain.filter(exchange)
                                            .doOnSubscribe(sub -> log.info("before: " + uri))
                                            .doOnEach(signal -> log.info("processing: " + uri))
                                            .doOnTerminate(() -> log.info("after: " + uri + ". " + "The response status code was"
                                            + exchange.getResponse().getStatusCode() + "."));

                                }))
                        )
                        .uri("http://httpbin.org")
                )
                .build();
    }
}
