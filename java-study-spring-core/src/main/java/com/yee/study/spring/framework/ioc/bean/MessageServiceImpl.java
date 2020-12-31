package com.yee.study.spring.framework.ioc.bean;

import org.springframework.stereotype.Component;

/**
 * 消息服务实现类
 *
 * @author Roger.Yi
 */
public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage() {
        return "hello world";
    }
}
