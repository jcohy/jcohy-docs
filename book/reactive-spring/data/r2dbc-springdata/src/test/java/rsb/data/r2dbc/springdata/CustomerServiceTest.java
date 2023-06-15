package rsb.data.r2dbc.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import rsb.data.r2dbc.BaseCustomerServiceTest;
import rsb.data.r2dbc.SimpleCustomerRepository;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:16:12
 * @since 2022.04.0
 */
@SpringBootTest
@EnableTransactionManagement
public class CustomerServiceTest extends BaseCustomerServiceTest {

    @Autowired
    private SimpleCustomerRepository customerRepository;
    @Override
    public SimpleCustomerRepository getCustomerRepository() {
        return this.customerRepository;
    }
}
