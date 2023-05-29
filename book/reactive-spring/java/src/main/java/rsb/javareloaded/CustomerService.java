package rsb.javareloaded;

import java.util.Collection;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:18:18
 * @since 2022.04.0
 */
public interface CustomerService {

    Collection<Customer> save(String... names);

    Customer findById(Long id);

    Collection<Customer> findAll();
}
