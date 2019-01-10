package com.yee.study.java.core.io.nio;

import java.nio.channels.Selector;

public class SelectorTest {

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        selector.wakeup();
        selector.selectNow();
        selector.wakeup();
        selector.selectNow();
        selector.wakeup();
    }
}
