package rsb.orchestration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/2:16:35
 * @since 2022.04.0
 */
@RestController
public class CustomerRestController {

    private final int delayInMillis;

    private final Map<Integer,Customer> customers = Map.of(1,"Jane",
            2,"Mia",3,"Leroy",4,"Badhr",5,"Zhen",6, "Juliette",7,"Artem",
            8,"Michelle",9,"Eva",10,"Richard")
            .entrySet()
            .stream()
            .collect(Collectors.toConcurrentMap(Map.Entry::getKey, e -> new Customer(e.getKey(),e.getValue())));

    public CustomerRestController(@Value("${rsb.delay:200}") int delayInMillis) {
        this.delayInMillis = delayInMillis;
    }

    private Flux<Customer> from(Stream<Customer> customerStream, boolean delaySubscription) {
        return delaySubscription ? Flux.fromStream(customerStream)
                .delaySubscription(Duration.ofMillis(this.delayInMillis)) :
                Flux.fromStream(customerStream);
    }

    @GetMapping("/customers")
    Flux<Customer> customers(@RequestParam(required = false) Integer[] ids,
                                @RequestParam(required = false) boolean delay) {
        var customerStream = this.customers.values().stream();
        return Optional.ofNullable(ids)
                .map(Arrays::asList)
                .map(listOfIds -> from(customerStream.filter(customer -> {
                    var id = customer.id();
                    return listOfIds.contains(id);
                }),delay))
                .orElse(from(customerStream,delay));

    }
}
