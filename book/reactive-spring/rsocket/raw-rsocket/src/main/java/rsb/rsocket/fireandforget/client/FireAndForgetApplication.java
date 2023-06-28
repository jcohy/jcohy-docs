package rsb.rsocket.fireandforget.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:16:11
 * @since 2022.04.0
 */
@SpringBootApplication
public class FireAndForgetApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(FireAndForgetApplication.class,args);
        Thread.currentThread().join();
    }
}
