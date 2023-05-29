package rsb.ws.echo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.test.StepVerifier;
import rsb.HttpApplication;
import rsb.ws.WebsocketConfiguration;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;

@Slf4j
@SpringBootTest(classes = { HttpApplication.class, WebsocketConfiguration.class },
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EchoWebsocketConfigurationTest {

	@LocalServerPort
	private int port;

	@Test
	public void testNotificationsOnUpdates() {
		var socketClient = new ReactorNettyWebSocketClient();
		var max = 2;
		var values = new ArrayList<>();
		var uri = URI.create("ws://localhost:" + this.port + "/ws/echo");
		var execute = socketClient.execute(uri, session -> {
			var map = session.receive() //
					.map(WebSocketMessage::getPayloadAsText) //
					.map(str -> str + " reply") //
					.doOnNext(values::add) //
					.map(session::textMessage) //
					.take(max);

			return session.send(map).then();
		});
		StepVerifier //
				.create(execute) //
				.expectComplete() //
				.verify(Duration.ofSeconds(max + 2));

		Assertions.assertEquals(max, values.size());
	}

}