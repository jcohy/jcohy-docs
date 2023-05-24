package com.jcohy.docs.reactive_spring.chapter7.webclient.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:38
 * @since 2022.04.0
 */
@SpringBootApplication
@EnableConfigurationProperties(ClientProperties.class)
public class HttpClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(HttpClientApplication.class)
                        .profiles("client")
                                .run(args);
    }

}
