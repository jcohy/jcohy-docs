package rsb.view;

import com.jcohy.docs.reactive_spring.chapter7.webflux.utils.IntervalMessageProducer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/23:15:58
 * @since 2022.04.0
 */
@Controller
public class TickerSseController {

    // <1>
    @GetMapping("/ticker.php")
    String initialView() {
        return "ticker";
    }

    // <2>
    @GetMapping(produces = TEXT_EVENT_STREAM_VALUE,value = "ticker-stream")
    String streamingUpdates(Model model) {
        var updates = new ReactiveDataDriverContextVariable(IntervalMessageProducer.produce(),1); // <3>
        model.addAttribute("updates",updates);
        return "ticker :: #updateBlock";
    }
}
