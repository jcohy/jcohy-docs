package rsb.reactor;//package com.jcohy.reactor;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import reactor.core.publisher.EmitterProcessor;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Consumer;
//
///**
// * 描述: .
// * <p>
// * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
// * </p>
// *
// * @author jiac
// * @version 2022.04.0 2023/5/6:17:03
// * @since 2022.04.0
// */
//public class HotStreamTest1 {
//
//    @Test
//    public void hot() {
//
//        var first = new ArrayList<Integer>();
//        var second = new ArrayList<Integer>();
//
//        var emitter = EmitterProcessor.<Integer>create(2);
//        var sink = emitter.sink();
//
//        emitter.subscribe(collect(first));
//        sink.next(1);
//        sink.next(2);
//
//        emitter.subscribe(collect(second));
//        sink.next(3);
//        sink.complete();
//
//        Assertions.assertTrue(first.size() > second.size());// <1>
//
//    }
//
//    Consumer<Integer> collect(List<Integer> collection) {
//        return collection::add;
//    }
//
//}
