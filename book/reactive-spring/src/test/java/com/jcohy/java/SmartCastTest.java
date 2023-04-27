package com.jcohy.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:16:20
 * @since 2022.04.0
 */
public class SmartCastTest {

    @Test
    void casts() {
        interface Animal {
            String speak();
        }

        class Cat implements Animal {

            @Override
            public String speak() {
                return "meow!";
            }
        }

        class Dog implements Animal {

            @Override
            public String speak() {
                return "woof!";
            }
        }

        var newPet = Math.random() < .5 ? new Cat() : new Dog();
        var message = new HashSet<String>();

        if (newPet instanceof Cat) {
            var cat = (Cat) newPet;
            message.add("the cat says " + cat.speak());
        }

        if(newPet instanceof Cat cat) {
            message.add("the cat says " + cat.speak());
        }

        if(newPet instanceof Dog dog) {
            message.add("the dog says " + dog.speak());
        }

        Assertions.assertEquals(message.size(),1);
        Assertions.assertTrue(message.contains("the dog says woof!") || message.contains("the cat says meow!"));
    }
}
