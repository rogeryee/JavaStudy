package com.yee.study.mysoa.spring.parser;

import com.yee.study.mysoa.spring.bean.Protocol;
import com.yee.study.mysoa.spring.bean.Reference;
import com.yee.study.mysoa.spring.bean.Registry;
import com.yee.study.mysoa.spring.bean.Service;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * MySOA Spring解析处理类
 *
 * @author Roger.Yi
 */
public class MySOANamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParser(Registry.class));
        registerBeanDefinitionParser("protocol", new ProtocolBeanDefinitionParser(Protocol.class));
        registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser(Reference.class));
        registerBeanDefinitionParser("service", new ServiceBeanDefinitionParser(Service.class));
    }
}
