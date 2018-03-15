package com.yee.study.java.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 心跳监测响应处理类
 *
 * @author Roger.Yi
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(HeartBeatRespHandler.class);
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        Message message = (Message) msg;
        // 返回心跳应答消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageTypes.HEARTBEAT_REQ)
        {
            LOG.info("Receive client heart beat message : ---> " + message);
            Message heartBeat = buildHeatBeat();
            LOG.info("Send heart beat response message to client : ---> " + heartBeat);
            ctx.writeAndFlush(heartBeat);
        }
        else
            ctx.fireChannelRead(msg);
    }

    private Message buildHeatBeat()
    {
        Message message = new Message();
        Header header = new Header();
        header.setType(MessageTypes.HEARTBEAT_RESP);
        message.setHeader(header);
        return message;
    }
}
