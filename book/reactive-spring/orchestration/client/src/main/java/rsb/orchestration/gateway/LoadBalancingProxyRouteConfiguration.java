package rsb.orchestration.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:11:55
 * @since 2022.04.0
 */
@Profile("routes-lb")
@Configuration
public class LoadBalancingProxyRouteConfiguration {

    @Bean
    RouteLocator gateway(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(rs -> rs.alwaysTrue().uri("lb://error-service"))
                .build();
    }
}
