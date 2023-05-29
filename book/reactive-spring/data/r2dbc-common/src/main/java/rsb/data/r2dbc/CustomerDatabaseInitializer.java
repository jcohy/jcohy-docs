package rsb.data.r2dbc;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.FileCopyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:15:30
 * @since 2022.04.0
 */
@Component
public class CustomerDatabaseInitializer {

    private final TransactionalOperator operator;

    private final DatabaseClient client;

    private final String sql;

    private final SimpleCustomerRepository repository;

    public CustomerDatabaseInitializer(@Value("classpath:/schema.sql") Resource resource,
                                       DatabaseClient client,
                                       SimpleCustomerRepository repository,
                                       TransactionalOperator operator) throws IOException {
        this.client = client;
        this.repository = repository;
        this.operator = operator;
        try(var in = new InputStreamReader(resource.getInputStream())) {
            this.sql = FileCopyUtils.copyToString(in);
        }
    }

    public Publisher<Void> resetCustomerTable() {
        Mono<Void> createSchema = client.sql(this.sql).then();

        Flux<Void> findAndDelete = repository.findAll()
                .flatMap(customer -> repository.deleteById(customer.id())); // <1>

        return createSchema.thenMany(this.operator.execute(status -> findAndDelete));
    }
}
