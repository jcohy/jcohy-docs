package com.jcohy.docs.reactive_spring.chapter5;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.DecoratingProxy;
import reactor.core.publisher.Flux;

import java.util.concurrent.ScheduledExecutorService;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:17:36
 * @since 2022.04.0
 */
@SpringBootApplication
@ImportRuntimeHints(Reactorapplication.Hints.class)
public class Reactorapplication {

    public static void main(String[] args) {
        SpringApplication.run(Reactorapplication.class);
    }

    static class Hints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            Flux.just(1);
            var prefix = "reactor.core.publisher.Traces";
            var classes = new String[] {
                    prefix,prefix + "$StackWalkerCallSiteSupplierFactory",
                    prefix + "$SharedSecretsCallSiteSupplierFactory",
                    prefix + "$ExceptionCallSiteSupplierFactory"
            };

            for (var c: classes) {
                hints.reflection().registerType(TypeReference.of(c), MemberCategory.values());
                hints.proxies().registerJdkProxy(ScheduledExecutorService.class, SpringProxy.class,
                        Advised.class, DecoratingProxy.class);
            }
        }
    }
}
