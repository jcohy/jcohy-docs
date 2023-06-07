package rsb.orchestration.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:11:54
 * @since 2022.04.0
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        System.setProperty("server.port","8080");
        SpringApplication.run(GatewayApplication.class,args);
    }
}
