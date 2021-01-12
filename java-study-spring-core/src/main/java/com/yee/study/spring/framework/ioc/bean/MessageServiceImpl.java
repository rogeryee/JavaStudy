package com.yee.study.spring.framework.ioc.bean;

import com.yee.study.util.StringUtil;

/**
 * 消息服务实现类
 *
 * @author Roger.Yi
 */
public class MessageServiceImpl implements MessageService {

    private String name;

    @Override
    public String getMessage() {
        return "hello world" + (StringUtil.isBlank(name) ? "" : ", " + name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
