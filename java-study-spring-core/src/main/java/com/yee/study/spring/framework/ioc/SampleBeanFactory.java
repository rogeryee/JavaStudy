package com.yee.study.spring.framework.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SampleBean工厂类
 *
 * 用于生成SampleBean的实例
 * 
 * @author Roger.Yi
 */
public class SampleBeanFactory
{
    private static Logger logger = LoggerFactory.getLogger(SampleBeanFactory.class);

    public static SampleBean createSampleBean()
    {
        SampleBean ret = new SampleBean();

        logger.info("createSampleBean() is invoked and bean = " + ret.toString());

        return ret;
    }

    public SampleBean createSampleBeanInstance()
    {
        SampleBean ret = new SampleBean();

        logger.info("createSampleBeanInstance() is invoked and bean = " + ret.toString());

        return ret;
    }
}
