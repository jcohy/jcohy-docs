package rsb.security.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/25:15:44
 * @since 2022.04.0
 */
@RestController
public class GreetingsController {

    @GetMapping("/greetings")
    Mono<Map<String,String>> greet(@AuthenticationPrincipal Mono<UserDetails> user) { // <1>
        return user
                .map(UserDetails::getUsername)
                .map(name -> Map.of("greetings","Hello" + name + " @ " + Instant.now() + "!"));
    }
}
