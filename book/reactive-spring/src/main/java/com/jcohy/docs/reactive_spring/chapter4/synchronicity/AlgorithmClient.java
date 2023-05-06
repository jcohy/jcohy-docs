package com.jcohy.docs.reactive_spring.chapter4.synchronicity;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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
@Service
class AlgorithmClient {

    private final Executor executor;

    private final Algorithm algorithm;

    public AlgorithmClient(Executor executor, Algorithm algorithm) {
        this.executor = executor;
        this.algorithm = algorithm;
    }

    // <1>
    public BigInteger calculate(int n) {
        return this.algorithm.compute(n);
    }

    // <2>
    public CompletableFuture<BigInteger> calculateWithCompletableFuture(int n) {
        var cf = new CompletableFuture<BigInteger>();
        this.executor.execute(() -> cf.complete(this.algorithm.compute(n)));
        return cf;
    }

    // <3>
    @Async
    public CompletableFuture<BigInteger> calculateWithAsync(int n) {
        return CompletableFuture.completedFuture(this.algorithm.compute(n));
    }

}
