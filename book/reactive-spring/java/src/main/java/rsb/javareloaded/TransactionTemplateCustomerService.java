package rsb.javareloaded;

import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/27:16:16
 * @since 2022.04.0
 */
public class TransactionTemplateCustomerService extends BaseCustomerService {

    private final TransactionTemplate transactionTemplate; // <1>

    public TransactionTemplateCustomerService(DataSource dataSource,
                                              TransactionTemplate transactionTemplate) {
        super(dataSource);
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Collection<Customer> save(String... names) {
        return this.transactionTemplate.execute(s -> super.save(names));
    }

    @Override
    public Customer findById(Long id) {
        return this.transactionTemplate.execute(s -> super.findById(id));
    }

    @Override
    public Collection<Customer> findAll() {
        return this.transactionTemplate.execute(s -> super.findAll());
    }
}
