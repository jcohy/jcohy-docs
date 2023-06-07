package rsb.orchestration.resilience4j;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static rsb.orchestration.TimerUtils.monitor;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:12:59
 * @since 2022.04.0
 */
abstract class GreetingClientUtils {

    static Mono<String> getGreetingFor(WebClient http, String clientUid, String path) {
        var parameterizedTypeReference = new ParameterizedTypeReference<Map<String,String>>(){};

        var monoFromHttpCall = http
                .get().uri("http://error-service/" + path + "?uid=" + clientUid)
                .retrieve()
                .bodyToMono(parameterizedTypeReference)
                .map(map -> map.get("greeting"));
        return monitor(monoFromHttpCall);
    }
}
