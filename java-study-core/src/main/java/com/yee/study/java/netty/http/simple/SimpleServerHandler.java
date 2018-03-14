package com.yee.study.java.netty.http.simple;

import com.yee.study.util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 简单的服务端请求处理器
 *
 * @author Roger.Yi
 */
public class SimpleServerHandler extends SimpleChannelInboundHandler<HttpObject>
{
    private static final Logger logger = LoggerFactory.getLogger(SimpleServerHandler.class);

    private final StringBuilder sb = new StringBuilder();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.channel().close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception
    {
        logger.info("Read msg, className = " + msg.getClass().getName());

        if (msg instanceof HttpRequest)
        {
            HttpRequest request = (HttpRequest) msg;
            buildResponseHeader(request);
        }

        if (msg instanceof HttpContent)
        {
            readContent((HttpContent) msg);
            ctx.write(buildResponse());
            ctx.flush();
        }
    }

    /**
     * 读取请求内容
     *
     * @param httpContent
     */
    private void readContent(HttpContent httpContent)
    {
        ByteBuf buffer = httpContent.content();
        byte[] cliContent = new byte[buffer.readableBytes()];
        buffer.readBytes(cliContent);
        buffer.release();

        String content = new String(cliContent);
        logger.info("Msg from Client is " + (StringUtil.isBlank(content) ? "blank" : content));
    }

    /**
     * 构建响应
     *
     * @return
     */
    private FullHttpResponse buildResponse()
    {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(sb.toString()
                .getBytes()));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        return response;
    }

    /**
     * 构建响应头部
     *
     * @param request
     */
    private void buildResponseHeader(HttpRequest request)
    {
        sb.setLength(0);
        sb.append("WELCOME TO THE SIMPLE WEB SERVER\r\n");
        sb.append("===================================\r\n");
        sb.append("VERSION: " + request.protocolVersion().text() + "\r\n");
        sb.append("REQUEST_URI: " + request.uri() + "\r\n\r\n");

        // 输出HttpRequest的Header属性
        for (Map.Entry<String, String> entry : request.headers())
        {
            sb.append("HEADER: " + entry.getKey() + '=' + entry.getValue() + "\r\n");
        }
        sb.append("\r\n\r\n");

        QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri());
        Map<String, List<String>> uriAttributes = decoderQuery.parameters();
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet())
        {
            for (String attrVal : attr.getValue())
            {
                sb.append("URI: " + attr.getKey() + '=' + attrVal + "\r\n");
            }
        }
        sb.append("\r\n\r\n");
    }
}
