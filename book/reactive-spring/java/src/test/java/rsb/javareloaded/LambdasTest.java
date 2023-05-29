package rsb.javareloaded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:16:09
 * @since 2022.04.0
 */
public class LambdasTest {

    @Test
    void lambda() {
        Function<String,Integer> stringIntegerFunction = str -> 2;

        interface MyHandler{
            String handler(String one, Integer two);
        }

        MyHandler withExplicit = (one, two) -> one + ":" + two;

        Assertions.assertEquals(stringIntegerFunction.apply(""),2);
        Assertions.assertEquals(withExplicit.handler("one",2),"one:2");

        var withVar = (MyHandler)(one, two) -> one + ":" + two;
        Assertions.assertEquals(withVar.handler("one",2),"one:2");

        MyHandler delegate = this::doHandler;
        Assertions.assertEquals(delegate.handler("one",2),"one:2");
    }

    private String doHandler(String one, Integer two) {
        return one + ":" + two;
    }
}
