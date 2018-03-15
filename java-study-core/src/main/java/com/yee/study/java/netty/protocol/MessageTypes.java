package com.yee.study.java.netty.protocol;

/**
 * 消息类型
 * @author Roger.Yi
 */
public final class MessageTypes
{
    public static final byte LOGIN_REQ = 'Q';

    public static final byte LOGIN_RESP = 'R';

    public static final byte HEARTBEAT_REQ = 'P';

    public static final byte HEARTBEAT_RESP = 'G';
}
