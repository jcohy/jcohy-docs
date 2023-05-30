package rsb.testing.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:16:06
 * @since 2022.04.0
 */
@Configuration
public class CustomerClientConfiguration {

    @Bean
    WebClient myWebClient(WebClient.Builder builder) {
        return builder.build();
    }
}
