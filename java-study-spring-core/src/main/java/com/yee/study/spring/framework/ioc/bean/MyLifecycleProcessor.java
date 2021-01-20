package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.LifecycleProcessor;

/**
 * 自定义 LifecycleProcessor
 * @author Roger.Yi
 */
@Slf4j
public class MyLifecycleProcessor implements LifecycleProcessor {

    private volatile boolean running;

    @Override
    public void onRefresh() {
        this.running = true;
        log.info("MyLifecycleProcessor.onRefresh()");
    }

    @Override
    public void onClose() {
        this.running = false;
        log.info("MyLifecycleProcessor.onClose()");
    }

    @Override
    public void start() {
        this.running = true;
        log.info("MyLifecycleProcessor.start()");
    }

    @Override
    public void stop() {
        this.running = false;
        log.info("MyLifecycleProcessor.stop()");
    }

    @Override
    public boolean isRunning() {
        log.info("MyLifecycleProcessor.isRunning()");
        return this.running;
    }
}
