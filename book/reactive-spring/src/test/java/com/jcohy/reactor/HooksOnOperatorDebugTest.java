package com.jcohy.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:16:20
 * @since 2022.04.0
 */
public class HooksOnOperatorDebugTest {

    @Test
    public void onOperatorDebug() {
        Hooks.onOperatorDebug();
        var stackTrace = new AtomicReference<String>();
        var errorFlux = Flux
                .error(new IllegalArgumentException("Oops!"))
                .checkpoint()
                .delayElements(Duration.ofMillis(1));

        StepVerifier.create(errorFlux)
                .expectErrorMatches(ex -> {
                    stackTrace.set(stackTraceToString(ex));
                    return ex instanceof IllegalArgumentException;
                })
                .verify();

        Assertions.assertTrue(stackTrace.get().contains("Flux.error ⇢ at " + HooksOnOperatorDebugTest.class.getName()));
    }

    private static String stackTraceToString(Throwable throwable) {
        try(var sw = new StringWriter();var pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
        catch (Exception ioEx) {
            throw new RuntimeException(ioEx);
        }
    }

}
