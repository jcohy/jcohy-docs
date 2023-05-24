package com.jcohy.docs.reactive_spring.chapter7.webclient.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:41
 * @since 2022.04.0
 */
@Configuration
public class HttpSecurityConfiguration {

    // <1>
    @Bean
    MapReactiveUserDetailsService authentication() {
        var jlong = User.withDefaultPasswordEncoder()
                .username("jlong")
                .roles("USER")
                .password("pw") // <2>
                .build();
        return new MapReactiveUserDetailsService(jlong);
    }

    // <3>
    @Bean
    SecurityWebFilterChain authorization(ServerHttpSecurity http) {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(spec -> spec
                        .pathMatchers("/greep/authenticated")
                        .authenticated()
                        .anyExchange()
                        .permitAll())
                .build();

    }
}
