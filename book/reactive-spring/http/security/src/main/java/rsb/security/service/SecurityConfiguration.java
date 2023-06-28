package rsb.security.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/25:15:31
 * @since 2022.04.0
 */
@Configuration
public class SecurityConfiguration {

    // <1>
    @Bean
    MapReactiveUserDetailsService authentication() {
        UserDetails jlong = User.withDefaultPasswordEncoder().username("jlong").password("pw").roles("USER").build();
        UserDetails rwinch = User.withDefaultPasswordEncoder().username("rwinch").password("pw").roles("USER","ADMIN").build();
        return new MapReactiveUserDetailsService(jlong,rwinch);
    }

    // <2>
    @Bean
    SecurityWebFilterChain authorization(ServerHttpSecurity http) {
        return http
                .httpBasic(Customizer.withDefaults()) // <3>
                .authorizeExchange(ae -> ae // <4>
                        .pathMatchers("/greetings").authenticated() // <5>
                        .anyExchange().permitAll() // <6>
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}
