package rsb.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
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
 * @version 2022.04.0 2023/5/8:16:37
 * @since 2022.04.0
 */
public class CheckpointTest {

    @Test
    public void checkPoint() {

        var stackTrace = new AtomicReference<String>();

        var checkPoint = Flux
                .error(new IllegalArgumentException("Oops!"))
                .checkpoint()
                .delayElements(Duration.ofMillis(1));

        StepVerifier.create(checkPoint)
                .expectErrorMatches(ex -> {
                    stackTrace.set(stackTraceToString(ex));
                    return ex instanceof IllegalArgumentException;
                })
                .verify();

        Assertions.assertTrue(stackTrace.get().contains("Error has been observed at the following site(s):"));
    }

    private static String stackTraceToString(Throwable throwable) {
        try(var sw = new StringWriter(); var pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
        catch (Exception ioEx) {
            throw new RuntimeException(ioEx);
        }
    }
}
