package com.yee.study.java.core.lang;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * WeakReference 弱引用 示例
 *
 * @author Roger.Yi
 */
public class WeakReferenceSample {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 当前弱引用在gc之后，会被清空
         */
        Reference<Apple> reference = new WeakReference<>(new Apple("红富士"));

        System.gc();
        Thread.sleep(5000);

        if (reference.get() == null) {
            System.out.println("reference was finalized.");
        }

        /**
         * 当前弱引用在gc之后，不会被清空，因为apple对象保留一个强引用
         */
        Apple apple = new Apple("国光");
        Reference<Apple> reference2 = new WeakReference<>(apple);

        System.gc();
        Thread.sleep(5000);

        if (reference2.get() == null) {
            System.out.println("reference2 was finalized.");
        }

        /**
         * 当前弱引用在gc之后，会被清空，因为apple对象在gc之前被显示的置为null
         */
        Apple apple2 = new Apple("红苹果");
        Reference<Apple> reference3 = new WeakReference<>(apple2);
        apple2 = null;

        System.gc();
        Thread.sleep(5000);

        if (reference3.get() == null) {
            System.out.println("reference3 was finalized.");
        }

        /**
         * 在创建WeakReference的时候，可以传入一个ReferenceQueue，这样当WeakReference被回收后，会进入queue
         */
        ReferenceQueue<Apple> weakRefQueue = new ReferenceQueue<>();
        Reference<Apple> reference4 = new WeakReference<>(new Apple("apple1"), weakRefQueue);
        Reference<Apple> reference5 = new WeakReference<>(new Apple("apple2"), weakRefQueue);
        Reference<Apple> reference6 = new WeakReference<>(new Apple("apple3"), weakRefQueue);

        System.gc();
        Thread.sleep(5000);

        if (reference4.get() == null) {
            System.out.println("reference4 was finalized.");
        }

        if (reference5.get() == null) {
            System.out.println("reference5 was finalized.");
        }

        if (reference6.get() == null) {
            System.out.println("reference6 was finalized.");
        }

        Reference<? extends Apple> ref = null;
        while ((ref = weakRefQueue.poll()) != null ) {
            System.out.println("weakRefQueue 中 ref：" + ref + ", obj : " + ref.get());
        }
    }

    static class Apple {

        private String name;

        public Apple(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("Apple: " + name + " finalized.");
        }
    }
}
