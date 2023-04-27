package com.jcohy.java.typeinterface;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:16:00
 * @since 2022.04.0
 */
public class LambdasAndTypeInterfaceTest {

    @FunctionalInterface
    interface MyHandler {
        String handle(String one,int two);
    }

    private String delegate(String s, Integer two) {
        return "Hello " + s + ":" + two;
    }

    @Test
    void lambda() {
        MyHandler defaultHandler = this::delegate;

        var withVar = new MyHandler() {

            @Override
            public String handle(String one, int two) {
                return delegate(one,two);
            }
        };

        var withCast = (MyHandler) this::delegate;

        var string = "hello";
        var integer = 2;

        var set = new HashSet<>(
                List.of(withCast.handle(string,integer),
                        withVar.handle(string,integer),
                        defaultHandler.handle(string,integer))
        );

        Assertions.assertEquals(set.size(),1,"the 3 entries should all be the same,and thus deduplicated out");
    }
}
