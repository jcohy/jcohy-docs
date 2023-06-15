package rsb.data.r2dbc.springdata;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.data.r2dbc.Customer;
import rsb.data.r2dbc.SimpleCustomerRepository;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/9:16:14
 * @since 2022.04.0
 */
@Component
public class SpringDataCustomerRepository implements SimpleCustomerRepository {

    private final CustomerRepository repository;

    public SpringDataCustomerRepository(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Customer> save(Customer c) {
        return repository.save(c);
    }

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Customer> update(Customer c) {
        return repository.save(c);
    }

    @Override
    public Mono<Customer> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return repository.deleteById(id);
    }
}
