package rsb.javareloaded.records;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:15:41
 * @since 2022.04.0
 */
public class RecordConstructorsTest {

    record Customer(Integer id, String eamil){

        Customer {
            Assert.notNull(id,() -> "the id must never be null");
            Assert.isTrue(StringUtils.hasText(eamil),() -> "the email is invalid");
        }

        Customer(String email) {
            this(-1,email);
        }
    }

    @Test
    void multipleConstructors() {
        var customer1 = new Customer("test@email.com");
        var customer2 = new Customer(2,"test@email.com");
        Assertions.assertEquals(customer1.id(),-1);
        Assertions.assertEquals(customer2.id(),2);
    }
}
