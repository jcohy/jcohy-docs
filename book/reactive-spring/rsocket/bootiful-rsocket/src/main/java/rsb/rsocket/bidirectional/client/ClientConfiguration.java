package rsb.rsocket.bidirectional.client;

import io.rsocket.SocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import rsb.rsocket.BootifulProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:10:33
 * @since 2022.04.0
 */
@Configuration
public class ClientConfiguration {

    // <1>
    @Bean
    SocketAcceptor clientRSocketFactoryConfigurer(HealthController healthController, RSocketStrategies socketStrategies) {
        return RSocketMessageHandler.responder(socketStrategies,healthController);
    }

    @Bean
    RSocketRequester rSocketRequester(SocketAcceptor socketAcceptor, RSocketRequester.Builder builder,
                                      BootifulProperties properties) {
        return builder
                .rsocketConnector( rcc -> rcc.acceptor(socketAcceptor))
                .tcp(properties.getrSocket().getHostname(),properties.getrSocket().getPort());
    }
}
