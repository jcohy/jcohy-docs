package rsb.data.r2dbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:15:45
 * @since 2022.04.0
 */
@Configuration
@Import({CustomerDatabaseInitializer.class})
public class CommonAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CommonAutoConfiguration.class);

    @Bean
    CustomerService defaultCustomerService(SimpleCustomerRepository repository,
                                           TransactionalOperator operator,
                                           CustomerDatabaseInitializer dbi) {
        return new CustomerService(repository,operator,dbi);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> demo(SimpleCustomerRepository repository) {
        return event -> log.info(String.valueOf(repository.save(new Customer(null,"josh@joshlong.com"))
                .thenMany(repository.findAll())
                .blockFirst()
        ));
    }
}
