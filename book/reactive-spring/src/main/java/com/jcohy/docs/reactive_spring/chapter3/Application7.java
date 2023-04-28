package com.jcohy.docs.reactive_spring.chapter3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/28:17:16
 * @since 2022.04.0
 */
@SpringBootApplication // <1>
public class Application7 {

    public static void main(String[] args) {

        System.setProperty("spring.profiles.active","prod"); // <2>

        SpringApplication.run(Application7.class,args); // <3>
    }
}

// <4>
@Profile("dev")
@Component
class DemoListen {
    private final CustomerService customerService;

    public DemoListen(CustomerService customerService) {
        this.customerService = customerService;
    }

    // <5>
    @EventListener(ApplicationReadyEvent.class)
    public void exercise() {

    }
}