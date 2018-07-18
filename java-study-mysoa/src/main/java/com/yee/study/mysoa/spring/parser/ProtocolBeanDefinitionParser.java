package com.yee.study.mysoa.spring.parser;

import com.yee.study.util.StringUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 协议Bean解析类
 *
 * @author Roger.Yi
 */
public class ProtocolBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ProtocolBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String name = element.getAttribute("name");
        String host = element.getAttribute("host");
        String port = element.getAttribute("port");

        if (StringUtil.isBlank(name)) {
            throw new RuntimeException("Attribute[name] is null or blank.");
        }
        if (StringUtil.isBlank(host)) {
            throw new RuntimeException("Attribute[host] is null or blank.");
        }
        if (StringUtil.isBlank(port)) {
            throw new RuntimeException("Attribute[port] is null or blank.");
        }

        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        beanDefinition.getPropertyValues().addPropertyValue("host", host);
        beanDefinition.getPropertyValues().addPropertyValue("port", port);
        parserContext.getRegistry().registerBeanDefinition("protocol" + host + port, beanDefinition);
        return beanDefinition;
    }
}
