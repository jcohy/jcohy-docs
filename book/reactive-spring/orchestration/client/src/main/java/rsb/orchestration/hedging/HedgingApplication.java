package rsb.orchestration.hedging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import rsb.orchestration.GreetingResponse;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:12:21
 * @since 2022.04.0
 */
@SpringBootApplication
public class HedgingApplication {

    private static final Logger log = LoggerFactory.getLogger(HedgingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HedgingApplication.class,args);
    }

    // <1>
    @Bean
    HedgingExchangeFilterFunction hedgingExchangeFilterFunction(@Value("${rsb.lb.max-nodes:3}") int maxNodes, ReactiveDiscoveryClient client) {
        return new HedgingExchangeFilterFunction(client,maxNodes);
    }

    // <2>
    @Bean
    WebClient client(WebClient.Builder builder,HedgingExchangeFilterFunction hedgingExchangeFilterFunction) {
        return builder.filter(hedgingExchangeFilterFunction).build();
    }

    // <3>
    @Bean
    ApplicationListener<ApplicationReadyEvent> hedgingApplicationListener(WebClient client) {
        return event -> client
                .get()
                .uri("http://slow-service/greetings")
                .retrieve()
                .bodyToFlux(GreetingResponse.class)
                .doOnNext(gr -> log.info(gr.toString()))
                .doOnError(ex -> log.info(ex.toString()))
                .subscribe();
    }

}
