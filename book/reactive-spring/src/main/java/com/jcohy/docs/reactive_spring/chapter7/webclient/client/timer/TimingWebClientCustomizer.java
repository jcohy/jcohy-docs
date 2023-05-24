package com.jcohy.docs.reactive_spring.chapter7.webclient.client.timer;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:55
 * @since 2022.04.0
 */
@Component
public class TimingWebClientCustomizer implements WebClientCustomizer {

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.filter(new TimingExchangeFilterFunction()); // <1>
    }
}
