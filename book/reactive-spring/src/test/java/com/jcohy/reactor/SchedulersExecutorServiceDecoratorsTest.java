package com.jcohy.reactor;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/6:14:41
 * @since 2022.04.0
 */
public class SchedulersExecutorServiceDecoratorsTest {

    private static final Logger log = LoggerFactory.getLogger(SchedulersExecutorServiceDecoratorsTest.class);

    private final AtomicInteger methodInvocationCounts = new AtomicInteger();

    private String rsb = "rsb";

    private ScheduledExecutorService decorate(ScheduledExecutorService executorService) {
        try {
            var pfb = new ProxyFactory();
            pfb.setInterfaces(ScheduledExecutorService.class);
            pfb.addAdvice((MethodInterceptor) methodInvocation -> {
                var methodName = methodInvocation.getMethod().getName().toLowerCase();
                this.methodInvocationCounts.incrementAndGet();
                log.info("methodName: (" + methodName + ") incrementing...");
                return methodInvocation.proceed();
            });
            pfb.setTarget(executorService);
            return (ScheduledExecutorService) pfb.getProxy();
        }
        catch (Exception e) {
            log.error("something went wrong!", e);
        }
        return null;
    }

    @BeforeEach
    public void before() {
        // <1>
        Schedulers.resetFactory();
        // <2>
        Schedulers.addExecutorServiceDecorator(this.rsb,
                (scheduler, scheduledExecutorService) -> this.decorate(scheduledExecutorService));
    }

    @Test
    public void changeDefaultDecorator() {
        var integerFlux = Flux.just(1).delayElements(Duration.ofMillis(1));
        StepVerifier.create(integerFlux).thenAwait(Duration.ofMillis(10)).expectNextCount(1).verifyComplete();
        Assertions.assertEquals(1, this.methodInvocationCounts.get());
    }

    @AfterEach
    public void after() {
        Schedulers.resetFactory();
        Schedulers.removeExecutorServiceDecorator(this.rsb);
    }
}
