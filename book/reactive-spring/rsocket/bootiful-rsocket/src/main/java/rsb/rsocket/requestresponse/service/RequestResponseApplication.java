package rsb.rsocket.requestresponse.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:44
 * @since 2022.04.0
 */
@SpringBootApplication
public class RequestResponseApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","service"); // <1>
        SpringApplication.run(RequestResponseApplication.class,args);
    }
}