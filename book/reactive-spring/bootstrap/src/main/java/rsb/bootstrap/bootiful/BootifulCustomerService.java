package rsb.bootstrap.bootiful;

import org.springframework.stereotype.Service;
import rsb.bootstrap.enable.TransactionCustomerService;

import javax.sql.DataSource;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/28:17:45
 * @since 2022.04.0
 */
@Service // <1>
public class BootifulCustomerService extends TransactionCustomerService {
    public BootifulCustomerService(DataSource dataSource) {
        super(dataSource);
    }
}
