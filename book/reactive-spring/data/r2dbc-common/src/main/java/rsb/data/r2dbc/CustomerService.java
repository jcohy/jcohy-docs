package rsb.data.r2dbc;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:15:29
 * @since 2022.04.0
 */
@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final SimpleCustomerRepository repository;

    private final TransactionalOperator operator;

    private final CustomerDatabaseInitializer initializer;

    public Publisher<Void> resetDatabase() {
        return this.initializer.resetCustomerTable();
    }

    public CustomerService(SimpleCustomerRepository repository,
                           TransactionalOperator operator,
                           CustomerDatabaseInitializer initializer) {
        this.repository = repository;
        this.operator = operator;
        this.initializer = initializer;
    }

    // <1>
    public Flux<Customer> upsert(String email) {
        var customers = this.repository
                .findAll()
                .filter(customer -> customer.email().equalsIgnoreCase(email))
                .flatMap(match -> this.repository.update(new Customer(match.id(), email)))
                .switchIfEmpty(this.repository.save(new Customer(null,email)));

        var validateResults = erroeIfEmailsAreInvalid(customers);
        return this.operator.transactional(validateResults);
    }

    // <2>
    @Transactional
    public Flux<Customer> normalizeEmails() {
        return erroeIfEmailsAreInvalid(this.repository.findAll()
                .flatMap( x -> this.upsert(x.email().toUpperCase())));
    }

    private static Flux<Customer> erroeIfEmailsAreInvalid(Flux<Customer> input) {
        return input.filter(c -> c.email().contains("@"))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("the email needs to be of the form a@b.com")));
    }
}
