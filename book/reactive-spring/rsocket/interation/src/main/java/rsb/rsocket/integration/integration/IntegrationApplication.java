package rsb.rsocket.integration.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.rsocket.ClientRSocketConnector;
import org.springframework.integration.rsocket.RSocketInteractionModel;
import org.springframework.integration.rsocket.dsl.RSockets;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.rsocket.RSocketStrategies;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.integration.GreetingRequest;
import rsb.rsocket.integration.GreetingResponse;

import java.io.File;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:16:25
 * @since 2022.04.0
 */
@SpringBootApplication
public class IntegrationApplication {

    private static final Logger log = LoggerFactory.getLogger(IntegrationApplication.class);

    @Bean
    ClientRSocketConnector clientRSocketConnector(RSocketStrategies socketStrategies, BootifulProperties properties) { // <1>

        var clientRSocketConnector = new ClientRSocketConnector(properties.getrSocket().getHostname(),
                properties.getrSocket().getPort());

        clientRSocketConnector.setRSocketStrategies(socketStrategies);
        return clientRSocketConnector;
    }

    @Bean
    IntegrationFlow greetingFlow(@Value("${user.home}")File home, ClientRSocketConnector clientRSocketConnector) {
        var inboundFileAdapter = Files // <2>
                .inboundAdapter(new File(home,"in"))
                .autoCreateDirectory(true);

        return IntegrationFlow
                .from(inboundFileAdapter,ppller -> ppller.poller( pm -> pm.fixedRate(100))) // <3>
                .transform(new FileToStringTransformer()) // <4>
                .transform(String.class, GreetingRequest::new) // <5>
                .handle(RSockets
                        .outboundGateway("greetings") // <6>
                        .interactionModel(RSocketInteractionModel.requestStream)
                        .expectedResponseType(GreetingResponse.class)
                        .clientRSocketConnector(clientRSocketConnector)
                )
                .split() // <7>
                .channel(this.channel()) // <8>
                .handle((GenericHandler<GreetingResponse>) (payload,headers) -> { // <9>
                    log.info("-------------------");
                    log.info(payload.toString());
                    headers.forEach((k,v) -> log.info(k + '=' + v));
                    return null;
                })
                .get();
    }

    @Bean
    MessageChannel channel() {
        return MessageChannels.flux().get(); // <10>
    }
    public static void main(String[] args) {
        SpringApplication.run(IntegrationApplication.class,args);
    }
}
