package com.yee.study.mysoa.invoke;

import com.yee.study.mysoa.consts.Protocols;

import java.util.HashMap;
import java.util.Map;


/**
 * 远程调用类工厂
 *
 * @author Roger.Yi
 */
public class InvokeFactory {

    private static Map<String, Invoke> invokes = new HashMap<>();

    static {
        invokes.put(Protocols.HTTP, new HttpInvoke());
        invokes.put(Protocols.RMI, null);
        invokes.put(Protocols.NETTY, null);
    }

    /**
     * 获取远程调用类实例
     *
     * @param protocol 协议类型
     * @return
     */
    public static Invoke getInvoke(String protocol) {

        if (Protocols.isValid(protocol) == false)
            throw new RuntimeException("No valid protocol found for creating Invoke object, protocol=" + protocol);

        return invokes.get(protocol);
    }
}
