package com.yee.study.mysoa.consts;

/**
 * 协议类型
 *
 * @author Roger.Yi
 */
public final class Protocols {

    /**
     * HTTP方式
     */
    public static final String HTTP = "http";

    /**
     * RMI方式
     */
    public static final String RMI = "rmi";

    /**
     * NETTY方式
     */
    public static final String NETTY = "netty";

    public static String defProtocol() {
        return HTTP;
    }

    public static boolean isValid(String protocol) {
        return HTTP.equals(protocol) || NETTY.equals(protocol) || RMI.equals(protocol);
    }
}
