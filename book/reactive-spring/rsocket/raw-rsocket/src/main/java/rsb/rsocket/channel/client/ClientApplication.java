package rsb.rsocket.channel.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:17:38
 * @since 2022.04.0
 */
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ClientApplication.class,args);
        Thread.currentThread().join();
    }
}
