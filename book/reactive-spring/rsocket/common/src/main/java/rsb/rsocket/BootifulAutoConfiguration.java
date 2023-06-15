package rsb.rsocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:15:08
 * @since 2022.04.0
 */
@Configuration
@EnableConfigurationProperties(BootifulProperties.class)
public class BootifulAutoConfiguration {

    // <1>
    @Bean
    EncodingUtils encodingUtils(ObjectMapper objectMapper) {
        return new EncodingUtils(objectMapper);
    }

}
