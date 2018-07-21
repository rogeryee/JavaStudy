package com.yee.study.mysoa.invoke;

import java.lang.reflect.Method;

/**
 * 远程调用参数定义类
 *
 * @author Roger.Yi
 */
public class InvocationArg {
    
    private Method method;

    private Object[] args;

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
}
