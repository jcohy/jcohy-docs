package rsb.ws.chat;

import org.springframework.web.reactive.socket.WebSocketSession;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/20:14:31
 * @since 2022.04.0
 */
public record Connection(String id, WebSocketSession socketSession) {
}
