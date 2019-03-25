package com.yee.study.spring.cloud.bus.provider.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 自定义事件类
 *
 * @author Roger.Yi
 */
public class MyEvent extends RemoteApplicationEvent {

    private String type;

    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
