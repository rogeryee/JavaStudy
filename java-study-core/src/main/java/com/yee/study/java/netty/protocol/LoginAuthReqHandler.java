package com.yee.study.java.netty.protocol;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录请求处理类
 *
 * @author Roger.Yi
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(LoginAuthReqHandler.class);

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward to the
     * next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p/>
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(buildLoginReq());
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to
     * the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p/>
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        Message message = (Message) msg;

        // 如果是握手应答消息，需要判断是否认证成功
        if (message.getHeader() != null && message.getHeader().getType() == MessageTypes.LOGIN_RESP)
        {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0)
            {
                // 握手失败，关闭连接
                ctx.close();
            }
            else
            {
                LOG.info("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        }
        else
            //调用下一个channel链..
            ctx.fireChannelRead(msg);
    }

    /**
     * 构建登录请求
     */
    private Message buildLoginReq()
    {
        Message message = new Message();
        Header header = new Header();
        header.setType(MessageTypes.LOGIN_REQ);
        message.setHeader(header);
        return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.fireExceptionCaught(cause);
    }
}
