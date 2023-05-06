package com.jcohy.docs.reactive_spring.chapter4.synchronicity;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

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
record AsyncRunner(AlgorithmClient algorithm, int max) implements Runnable {

    @Override
    public void run() {
        // <1>
        executeCompletableFuture("calculateWithAsync", () -> algorithm.calculateWithAsync(max));
        // <2>
        executeCompletableFuture("calculateWithCompletableFuture", () -> algorithm.calculateWithCompletableFuture(max));
    }

    private static void executeCompletableFuture(String func,
                                                 Supplier<CompletableFuture<BigInteger>> completableFuture) {
        Timer.before(func);
        completableFuture.get().whenComplete((r, t) -> Timer.result(func, r));
        Timer.after(func);
    }
}
