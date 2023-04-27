package com.jcohy.java.records;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

import java.util.List;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:15:45
 * @since 2022.04.0
 */
public class SimpleRecordsTest {

    record Customer(Integer id,String name){}

    record Order(Integer id, double total){}

    record CustomerOrders(Customer customer, List<Order> orders){}

    @Test
    void records() {
        var customer = new Customer(253, "Tammie");
        var order1 = new Order(2232,74.023);
        var order2 = new Order(9593,23.44);
        var customerOrders = new CustomerOrders(customer,List.of(order1,order2));

        Assertions.assertEquals(order1.id(),2232);
        Assertions.assertEquals(order1.total(),74.023);
        Assertions.assertEquals(customer.name(),"Tammie");
        Assertions.assertEquals(customerOrders.orders().size(),2);
    }
}
