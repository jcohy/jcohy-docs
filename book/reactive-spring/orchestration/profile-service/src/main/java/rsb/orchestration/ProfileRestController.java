package rsb.orchestration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:10:53
 * @since 2022.04.0
 */
@RestController
public class ProfileRestController {

    private final Map<Integer,Profile> profiles = Map
            .of(1,"Jane",2,"mia",3,"leroy",4,"badhr",5,"zhen",6,"juliette",
            7,"artem",8,"michelle",9,"eva",10,"richard")
            .entrySet()
            .stream()
            .collect(Collectors.toConcurrentMap(Map.Entry::getKey, e -> new Profile(e.getKey(),e.getValue(), UUID.randomUUID().toString())));

    @GetMapping("/profiles/{id}")
    Mono<Profile> byId(@PathVariable Integer id) {
        return Mono.just(this.profiles.get(id));
    }
}
