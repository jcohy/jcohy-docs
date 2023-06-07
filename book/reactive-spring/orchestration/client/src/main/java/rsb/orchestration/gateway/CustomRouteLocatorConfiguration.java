package rsb.orchestration.gateway;

import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.SetPathGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:11:15
 * @since 2022.04.0
 */
@Configuration
@Profile("custom-route-locator")
public class CustomRouteLocatorConfiguration {

    @Bean
    RouteLocator customRouteLocator(SetPathGatewayFilterFactory factory) {  // <1>

        var setPathGatewayFilter = factory.apply(config -> config.setTemplate("/guides")); // <2>
        var orderedGatewayFilter = new OrderedGatewayFilter(setPathGatewayFilter,0); // <3>
        var singleRoute = Route // <4>
                .async()
                .id("spring-io-guides")
                .asyncPredicate(serverWebExchange -> Mono.just(true))
                .filter(orderedGatewayFilter)
                .uri("https://spring.io/")
                .build();

        return () -> Flux.just(singleRoute); // <5>
    }
}
