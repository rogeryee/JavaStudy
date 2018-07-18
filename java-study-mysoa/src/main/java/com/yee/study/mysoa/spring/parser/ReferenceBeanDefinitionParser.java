package com.yee.study.mysoa.spring.parser;

import com.yee.study.util.StringUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 引用关联Bean解析类
 *
 * @author Roger.Yi
 */
public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ReferenceBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String id = element.getAttribute("id");
        String intf = element.getAttribute("interface");
        String loadBalance = element.getAttribute("loadBalance");
        String protocol = element.getAttribute("protocol");

        if (StringUtil.isBlank(id)) {
            throw new RuntimeException("Attribute[id] is null or blank.");
        }
        if (StringUtil.isBlank(intf)) {
            throw new RuntimeException("Attribute[intf] is null or blank.");
        }
        if (StringUtil.isBlank(loadBalance)) {
            throw new RuntimeException("Attribute[loadBalance] is null or blank.");
        }
        if (StringUtil.isBlank(protocol)) {
            throw new RuntimeException("Attribute[protocol] is null or blank.");
        }

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("intf", intf);
        beanDefinition.getPropertyValues().addPropertyValue("loadBalance", loadBalance);
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
        parserContext.getRegistry().registerBeanDefinition("Reference" + id + intf, beanDefinition);
        return beanDefinition;
    }
}
