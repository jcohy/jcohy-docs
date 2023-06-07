package rsb.orchestration.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:15:15
 * @since 2022.04.0
 */
@SpringBootApplication
public class ResilientClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResilientClientApplication.class,args);
    }
}
