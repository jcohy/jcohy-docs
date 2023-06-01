package rsb.rsocket.metadata.service;

import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.StringDecoder;
import rsb.rsocket.metadata.Constants;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:53
 * @since 2022.04.0
 */
@Configuration
public class ServiceConfiguration {

    @Bean
    RSocketStrategiesCustomizer rSocketStrategiesCustomizer () {
        return strategies -> strategies
                .metadataExtractorRegistry( registry -> {
                    // <1>
                    registry.metadataToExtract(Constants.CLIENT_ID, String.class, Constants.CLIENT_ID_HEADER);
                    registry.metadataToExtract(Constants.LANG,String.class,Constants.LANG_HEADER);
                })
                .decoders(decoders -> decoders.add(StringDecoder.allMimeTypes()));
    }
}
