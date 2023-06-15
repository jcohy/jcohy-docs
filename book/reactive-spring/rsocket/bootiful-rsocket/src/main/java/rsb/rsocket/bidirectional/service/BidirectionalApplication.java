package rsb.rsocket.bidirectional.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:10:26
 * @since 2022.04.0
 */
@SpringBootApplication
public class BidirectionalApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","service");
        SpringApplication.run(BidirectionalApplication.class,args);
    }
}
