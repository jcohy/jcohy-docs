package rsb.rsocket.metadata.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import rsb.rsocket.BootifulProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:33
 * @since 2022.04.0
 */
@Configuration
public class ClientConfiguration {

    @Bean
    RSocketRequester rSocketRequester(BootifulProperties properties,RSocketRequester.Builder builder) {
        return builder
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON) // <1>
                .tcp(properties.getrSocket().getHostname(),properties.getrSocket().getPort());
    }
}
