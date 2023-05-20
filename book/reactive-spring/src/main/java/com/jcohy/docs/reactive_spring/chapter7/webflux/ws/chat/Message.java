package com.jcohy.docs.reactive_spring.chapter7.webflux.ws.chat;

import java.util.Date;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/20:14:32
 * @since 2022.04.0
 */
public record Message(String clientId, String text, Date when) {
}
