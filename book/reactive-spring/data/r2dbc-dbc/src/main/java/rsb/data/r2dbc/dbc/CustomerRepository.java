package rsb.data.r2dbc.dbc;


import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.data.r2dbc.Customer;
import rsb.data.r2dbc.SimpleCustomerRepository;

import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/9:15:38
 * @since 2022.04.0
 */
public class CustomerRepository implements SimpleCustomerRepository {

    private final DatabaseClient databaseClient;

    private Customer map(Map<String, Object> row) {
        return new Customer((Integer) row.get("id"),(String) row.get("email"));
    }
    public CustomerRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Customer> save(Customer c) {
        return databaseClient.sql("insert into customer ( email ) values ($1)")
                .bind("$1",c.email())
                .filter((stmt,ef) -> stmt.returnGeneratedValues("id").execute())
                .fetch()
                .first()
                .flatMap(row -> findById((Integer) row.get("id")));
    }

    @Override
    public Flux<Customer> findAll() {
        return databaseClient.sql("select * from customer")
                .fetch()
                .all()
                .as(rows -> rows.map(this::map));
    }

    @Override
    public Mono<Customer> update(Customer c) {
        return databaseClient.sql("update customer set email = $1 where id = $2")
                .bind("$1",c.email())
                .bind("$2",c.id())
                .fetch()
                .first()
                .switchIfEmpty(Mono.empty())
                .then(findById(c.id()));

    }

    @Override
    public Mono<Customer> findById(Integer id) {
        return databaseClient.sql("select * from customer where id = $1")
                .bind("$1",id)
                .fetch()
                .first()
                .map(map -> new Customer((Integer) map.get("id"),(String)map.get("email")));
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return databaseClient.sql("delete from customer where id = $1")
                .bind("$1",id)
                .fetch()
                .rowsUpdated()
                .then();
    }
}
