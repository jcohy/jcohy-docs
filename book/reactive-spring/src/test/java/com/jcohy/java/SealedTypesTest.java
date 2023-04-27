package com.jcohy.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/24:15:13
 * @since 2022.04.0
 */
public class SealedTypesTest {

    sealed interface Shape permits Oval,Polygon {}

    static sealed class Oval implements Shape permits  Circle {}

    static final class Circle extends Oval{}

    static final class Polygon implements Shape {}

    private String describeShape(Shape shape) {
        Assert.notNull(shape,() -> "the shape should never be null!");
        if( shape instanceof Oval) {
            return "round";
        }
        if( shape instanceof Polygon) {
            return "straight";
        }

        throw new RuntimeException("we should never get to this point!");
    }

    @Test
    void disjointedUnionTypes() {
        Assertions.assertEquals(describeShape(new Oval()),"round");
        Assertions.assertEquals(describeShape(new Polygon()),"straight");
    }
}
