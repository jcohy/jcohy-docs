package rsb.orchestration.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.PrincipalNameKeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
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
 * @version 2022.04.0 2023/6/7:12:07
 * @since 2022.04.0
 */
@Profile("rl")
@Configuration
public class RateLimiterConfiguration {

    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(5,7);
    }
    @Bean
    RouteLocator gateway(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(routeSpec -> routeSpec
                        .path("/")
                        .filters(fs -> fs
                                .setPath("/ok")
                                .requestRateLimiter(rl -> rl
                                        .setRateLimiter(redisRateLimiter()) // <1>
                                        .setKeyResolver(new PrincipalNameKeyResolver()) // <2>

                                ))    .
                        uri("ls://error-service"))
                .build();
    }
}
