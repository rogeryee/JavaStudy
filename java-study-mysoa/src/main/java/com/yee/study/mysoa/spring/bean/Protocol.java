package com.yee.study.mysoa.spring.bean;

import com.yee.study.mysoa.rpc.netty.NettyUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.Serializable;

/**
 * 协议定义类
 *
 * @author Roger.Yi
 */
public class Protocol implements Serializable, ApplicationListener<ContextRefreshedEvent> {

    /**
     * ID
     */
    public String id;

    /**
     * 协议类型
     * {@link com.yee.study.mysoa.consts.Protocols}
     */
    private String name;

    /**
     * 端口
     */
    private String port;

    /**
     * Host地址
     */
    private String host;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!ContextRefreshedEvent.class.getName().equals(contextRefreshedEvent.getClass().getName())) {
            return;
        }

        if (!"netty".equalsIgnoreCase(name)) {
            return;
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    NettyUtil.startServer(port);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
