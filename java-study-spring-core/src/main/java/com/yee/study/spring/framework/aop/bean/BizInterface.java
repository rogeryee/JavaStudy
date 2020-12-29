package com.yee.study.spring.framework.aop.bean;

/**
 * 业务服务接口
 *
 * @author Roger.Yi
 */
public interface BizInterface {
    /**
     * 业务接口
     */
    String doBiz();

    /**
     * 业务接口
     */
    String cancelBiz();

    /**
     * 失败业务处理
     * @return
     */
    String fallback();
}
