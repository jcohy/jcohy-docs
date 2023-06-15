package rsb.orchestration.reactor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import rsb.orchestration.Order;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:17:08
 * @since 2022.04.0
 */
@Component
public record OrderClient(WebClient http) {

    Flux<Order> getOrders(Integer... ids) {
        var orderRoot = "http://order-service/orders?ids=" + StringUtils.arrayToDelimitedString(ids,",");
        return http.get().uri(orderRoot).retrieve().bodyToFlux(Order.class);
    }
}
