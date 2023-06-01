package rsb.rsocket.fireandforget.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import static org.slf4j.LoggerFactory.*;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:34
 * @since 2022.04.0
 */
@Controller
public class GreetingController {

    private static final Logger log = getLogger(GreetingController.class);

    @MessageMapping("greeting")
    void greetName(String name) {
        log.info("new command set to update the name '" + name + "'.");
    }
}
