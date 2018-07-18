package com.yee.study.mysoa.spring.parser;

import com.yee.study.util.StringUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 服务注册Bean解析类
 *
 * @author Roger.Yi
 */
public class RegistryBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public RegistryBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String protocol = element.getAttribute("protocol");
        String address = element.getAttribute("address");

        if (StringUtil.isBlank(protocol)) {
            throw new RuntimeException("Attribute[protocol] is null or blank.");
        }

        if (StringUtil.isBlank(address)) {
            throw new RuntimeException("Attribute[address] is null or blank.");
        }

        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
        beanDefinition.getPropertyValues().addPropertyValue("address", address);
        parserContext.getRegistry().registerBeanDefinition("Registry" + address, beanDefinition);
        return beanDefinition;
    }
}
