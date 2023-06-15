package rsb.synchronicity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
abstract class Timer {

    private static final Logger log = LoggerFactory.getLogger(Timer.class);

    private static final Map<String, Instant> starts = new ConcurrentHashMap<>();

    static void before(String func) {
        var now = Instant.now();
        starts.put(func, now);
        log.info("before " + func);
    }

    static void after(String func) {
        log.info("after " + func);
    }

    static void result(String func, Object result) {
        var now = Instant.now();
        var beginning = starts.get(func);
        var duration = now.toEpochMilli() - beginning.toEpochMilli();
        var durationString = (duration < 1000) //
                ? (duration + " milliseconds") //
                : ((duration / 1000) + "." + (duration % 1000) + " seconds");
        log.info("result of {} is {}. Task ran for {}", func, result, durationString);
    }

}
