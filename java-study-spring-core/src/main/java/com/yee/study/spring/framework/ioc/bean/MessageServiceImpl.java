package com.yee.study.spring.framework.ioc.bean;

import com.yee.study.util.StringUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息服务实现类
 *
 * @author Roger.Yi
 */
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Setter
    private String name;

    @Override
    public String getMessage() {
        return "hello world" + (StringUtil.isBlank(name) ? "" : ", " + name);
    }
}
