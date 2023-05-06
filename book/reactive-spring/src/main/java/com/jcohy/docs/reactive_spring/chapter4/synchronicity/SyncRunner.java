package com.jcohy.docs.reactive_spring.chapter4.synchronicity;

import java.math.BigInteger;
import java.util.function.Supplier;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:17:13
 * @since 2022.04.0
 */
record SyncRunner(AlgorithmClient algorithm, int max) implements Runnable {

    @Override
    public void run() {
        Timer.before("calculate");
        var results = ((Supplier<BigInteger>) () -> algorithm.calculate(this.max)).get();
        Timer.after("calculate");
        Timer.result("calculate", results);
    }
}
