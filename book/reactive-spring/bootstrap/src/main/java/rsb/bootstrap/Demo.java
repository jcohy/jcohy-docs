package rsb.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.stream.Stream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/27:13:06
 * @since 2022.04.0
 */
public class Demo {

    private static final Logger log = LoggerFactory.getLogger(Demo.class);

    public static void workWithCustomerService(Class<?> label, CustomerService customerService){
        // <1>
        log.info("================================");
        log.info(label.getName());
        log.info("================================");

        // <2>
        Stream.of("A","B","C").map(customerService::save)
                .forEach(customer -> {
                    log.info("saved " + customer.toString());
                });

        // <3>
        customerService.findAll()
                .forEach(customer -> {
                    var id = customer.id();
                    // <4>
                    var byId = customerService.findById(id);
                    log.info("found " + byId.toString());
                    Assert.notNull(byId,"the resulting customer should not be null!");
                    Assert.isTrue(byId.equals(customer),"we should be able to query for this result");
                });
    }
}
