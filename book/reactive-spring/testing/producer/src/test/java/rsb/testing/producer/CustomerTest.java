package rsb.testing.producer;

import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.hamcrest.Matchers;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:15:19
 * @since 2022.04.0
 */
public class CustomerTest {

    @Test
    void create() {
        var customer = new Customer("123","foo"); // <1>
        Assert.assertEquals(customer.id(),"123"); // <2>
//        org.hamcrest.MatcherAssert.assertThat(customer.id(), Matchers.is("123")); // <3>
        Assertions.assertEquals(customer.name(),"foo"); // <4>
    }
}
