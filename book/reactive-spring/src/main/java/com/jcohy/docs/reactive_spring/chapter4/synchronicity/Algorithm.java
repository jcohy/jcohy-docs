package com.jcohy.docs.reactive_spring.chapter4.synchronicity;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:17:12
 * @since 2022.04.0
 */
@Component
class Algorithm {

    public BigInteger compute(int num) {
        var result = new AtomicReference<BigInteger>();
        var factorial = factorial(num); // <1>
        iterate(factorial, result::set);// <2>
        return result.get();
    }

    private static void iterate(BigInteger i, Consumer<BigInteger> consumer) {
        for (var bi = BigInteger.ZERO; bi.compareTo(i) < 0; bi = bi.add(BigInteger.ONE)) {
            consumer.accept(bi);
        }
    }

    private static BigInteger factorial(int num) {
        var result = BigInteger.ONE;
        for (int i = 2; i <= num; i++)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

}
