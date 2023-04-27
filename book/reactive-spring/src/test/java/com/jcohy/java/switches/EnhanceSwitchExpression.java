package com.jcohy.java.switches;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:15:55
 * @since 2022.04.0
 */
public class EnhanceSwitchExpression {

    enum Emotion {
        HAPPY,SAD
    }

    @Test
    void switchExpression() {
        Assertions.assertEquals(respondToEmotionState(TraditionalSwitchExpression.Emotion.HAPPY),"that's wonderful.");
        Assertions.assertEquals(respondToEmotionState(TraditionalSwitchExpression.Emotion.SAD),"I'm so sorry to hear that.");
    }

    public String respondToEmotionState(TraditionalSwitchExpression.Emotion emotion) {
        return switch (emotion) {
            case HAPPY -> "that's wonderful.";
            case SAD -> "I'm so sorry to hear that.";
        };
    }
}
