package rsb.ws.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/20:14:33
 * @since 2022.04.0
 */
@Configuration
public class ChatWebsocketConfiguration {


    // <1>
    ChatWebsocketConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private final ObjectMapper objectMapper;

    // <2>
    private final Map<String, Connection> sessions = new ConcurrentHashMap<>();

    // <3>
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();

    @Bean
    WebSocketHandler chatWsh() {
        // <4>
        var messagesToBroadcast = Flux.<Message>create(sink -> {
            var submit = Executors.newSingleThreadExecutor().submit(() -> {
                while (true) {
                    try {
                        sink.next(this.messages.take());
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            sink.onCancel(() -> submit.cancel(true));
        })
                .share();

        return session -> { // <5>
          var sessionId = session.getId();
          this.sessions.put(sessionId,new Connection(sessionId,session));

          var in = session // <6>
                  .receive()
                  .map(WebSocketMessage::getPayloadAsText)
                  .map(this::messageFromJson)
                  .map(msg -> new Message(sessionId,msg.text(),new Date()))
                  .map(this.messages::offer)
                  .doFinally(st -> { // <7>
                     if(st.equals(SignalType.ON_COMPLETE)) {
                         this.sessions.remove(sessionId);
                     }
                  });

            var out = messagesToBroadcast // <8>
                    .map(this::jsonFromMessage)
                    .map(session::textMessage);

            return session.send(out).and(in);
        };
    }


    private Message messageFromJson(String json) {
        try {
            return this.objectMapper.readValue(json,Message.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String jsonFromMessage(Message msg) {
        try {
            return this.objectMapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    HandlerMapping chatHm() {
        return new SimpleUrlHandlerMapping(Map.of("/ws/chat" , chatWsh()),2);
    }
}
