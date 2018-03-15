package com.yee.study.java.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 心跳监测请求处理类
 *
 * @author Roger.Yi
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(HeartBeatReqHandler.class);

    //使用定时任务发送
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        Message message = (Message) msg;
        // 当握手成功后，Login响应向下透传，主动发送心跳消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageTypes.LOGIN_RESP)
        {
            //NioEventLoop是一个Schedule,因此支持定时器的执行，创建心跳计时器
            heartBeat = ctx.executor()
                    .scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
        }
        else if (message.getHeader() != null && message.getHeader().getType() == MessageTypes.HEARTBEAT_RESP)
        {
            LOG.info("Client receive server heart beat message : ---> " + message);
        }
        else
            ctx.fireChannelRead(msg);
    }

    //Ping消息任务类
    private class HeartBeatTask implements Runnable
    {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(final ChannelHandlerContext ctx)
        {
            this.ctx = ctx;
        }

        @Override
        public void run()
        {
            Message heatBeat = buildHeatBeat();
            LOG.info("Client send heart beat messsage to server : ---> " + heatBeat);
            ctx.writeAndFlush(heatBeat);
        }

        private Message buildHeatBeat()
        {
            Message message = new Message();
            Header header = new Header();
            header.setType(MessageTypes.HEARTBEAT_REQ);
            message.setHeader(header);
            return message;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        if (heartBeat != null)
        {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
