package com.yee.study.java.core.lang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Roger.Yi
 */
public class ThreadLocalSample {

    public static void main(String[] args) {
        addNumThreadLocal.set(1);
        addNumThreadLocal.set(2);
        System.out.println(addNumThreadLocal.get());
//        ExecutorService service = Executors.newFixedThreadPool(2);
//
//        for (int i = 0; i < 20; i++) {
//            int num = i;
//            service.execute(() -> {
//                System.out.println(num + " " + add10(num));
//            });
//        }
//        service.shutdown();
    }

    private static ThreadLocal<Integer> addNumThreadLocal = new ThreadLocal<>();

    public static int add10(int num) {
        addNumThreadLocal.set(num);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return addNumThreadLocal.get() + 10;
    }


}
