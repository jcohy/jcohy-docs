package rsb.data.mongodb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:15:05
 * @since 2022.04.0
 */
public interface CustomerRepository extends ReactiveMongoRepository<Customer,String> {

    @Tailable
    Flux<Customer> findByName(String name);
}
