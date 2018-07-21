package com.yee.study.mysoa.spring.parser;

import com.yee.study.util.StringUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 服务Bean解析类
 *
 * @author Roger.Yi
 */
public class ServiceBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ServiceBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String id = element.getAttribute("id");
        String intfClazz = element.getAttribute("intfClazz");
        String ref = element.getAttribute("ref");
        String protocol = element.getAttribute("protocol");

        if (StringUtil.isBlank(intfClazz)) {
            throw new RuntimeException("Attribute[intfClazz] is null or blank.");
        }
        if (StringUtil.isBlank(ref)) {
            throw new RuntimeException("Attribute[ref] is null or blank.");
        }
        if (StringUtil.isBlank(protocol)) {
            throw new RuntimeException("Attribute[protocol] is null or blank.");
        }

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("intfClazz", intfClazz);
        beanDefinition.getPropertyValues().addPropertyValue("ref", ref);
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
        parserContext.getRegistry().registerBeanDefinition("Service" + intfClazz + ref, beanDefinition);
        return beanDefinition;
    }
}
