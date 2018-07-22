package com.yee.study.mysoa.invoke;

import com.yee.study.mysoa.spring.bean.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 远程调用代理类
 *
 * @author Roger.Yi
 */
public class InvokeInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(InvokeInvocationHandler.class);

    // 远程调用
    private Invoke invoke;

    private Reference reference;

    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    /**
     * 实际调用远端服务
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("Invoke class=[{}] method=[{}]", proxy.getClass().toString(), method.toString());

        InvocationArg arg = new InvocationArg();
        arg.setMethod(method);
        arg.setArgs(args);
        arg.setReference(reference);
        return invoke.invoke(arg);
    }
}
