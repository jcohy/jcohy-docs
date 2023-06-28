package rsb.orchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:11:00
 * @since 2022.04.0
 */
@SpringBootApplication
public class SlowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlowServiceApplication.class,args);
    }
}
