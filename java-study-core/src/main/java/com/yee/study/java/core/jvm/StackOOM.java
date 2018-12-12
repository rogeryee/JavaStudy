package com.yee.study.java.core.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 栈内存溢出示例
 * 
 * @author Roger.Yi
 */
public class StackOOM {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackOOM.class);

    private static int count = 0;

    public static void main(String[] args) {
        StackOOM test = new StackOOM();
        test.oomMethod();
    }

    public void oomMethod() {
        for (int i = 0; i < 10000000; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        //此处应让线程进入休眠态，否则一直死循环，将耗尽计算机的计算资源，让系统宕机
                        try {
                            count++;
                            Thread.sleep(1000000);
                        } catch (InterruptedException e) {
                            System.out.println("开启" + count + "线程");
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
