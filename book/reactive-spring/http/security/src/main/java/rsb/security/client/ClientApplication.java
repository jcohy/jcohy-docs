package rsb.security.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

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
@SpringBootApplication
public class ClientApplication {

    private static final Logger log = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class,args);
    }

    // <1>
    @Bean
    WebClient webClient(WebClient.Builder builder) {
        var username = "jlong";
        var password = "pw";
        var basicAuthentication = ExchangeFilterFunctions.basicAuthentication(username,password);
        return builder
                .filter(basicAuthentication)
                .build();
    }

    // <2>
    @Bean
    ApplicationListener<ApplicationReadyEvent> client(WebClient securityHttpClient) {
        return event -> securityHttpClient
                .get()
                .uri("http://localhost:8080/greetings")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String,String>>() {
                }) // <3>
                .subscribe(map -> log.info("greeting: " + map.get("greetings")));
    }
}
