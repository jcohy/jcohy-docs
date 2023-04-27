package com.jcohy.java.typeinterface;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:15:56
 * @since 2022.04.0
 */
public class TypeInterfaceTest {

    @Test
    void infer() {
        var map1 = Map.of("key","value");
        Map<String,String> map2 = Map.of("key","value");
        Assertions.assertEquals(map2,map1);

        var anonymousSubclass = new Object() {
          final String name = "Peanut the Poodle";
          int age = 7;
        };

        Assertions.assertEquals(anonymousSubclass.age,7);
        Assertions.assertEquals(anonymousSubclass.name,"Peanut the Poodle");
    }
}
