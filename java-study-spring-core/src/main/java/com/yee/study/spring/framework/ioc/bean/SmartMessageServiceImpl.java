package com.yee.study.spring.framework.ioc.bean;

import com.yee.study.util.StringUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;

/**
 * 消息服务实现类(实现了 SmartLifecycle)
 *
 * @author Roger.Yi
 */
@Slf4j
public class SmartMessageServiceImpl implements MessageService, SmartLifecycle {

    @Setter
    private String name;

    @Override
    public String getMessage() {
        return "hello world" + (StringUtil.isBlank(name) ? "" : ", " + name);
    }

    // Implementation of SmartLifecycle interface
    @Override
    public void start() {
        log.info("SmartMessageServiceImpl.start");
    }

    @Override
    public void stop() {
        log.info("SmartMessageServiceImpl.stop");
    }

    @Override
    public boolean isRunning() {
        log.info("SmartMessageServiceImpl.isRunning");
        return true;
    }

    @Override
    public boolean isAutoStartup() {
        log.info("SmartMessageServiceImpl.isAutoStartup");
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        log.info("SmartMessageServiceImpl.stop");
    }

    @Override
    public int getPhase() {
        log.info("SmartMessageServiceImpl.getPhase");
        return 0;
    }
}
