package com.yee.study.mysoa.invoke;

import com.yee.study.mysoa.spring.bean.Reference;

import java.lang.reflect.Method;

/**
 * 远程调用参数定义类
 *
 * @author Roger.Yi
 */
public class InvocationArg {
    
    private Method method;

    private Object[] args;

    private Reference reference;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
