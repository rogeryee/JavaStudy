package com.yee.study.java.netty.http.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 自定义的通道处理器
 *
 * @author Roger.Yi
 */
public class ChunkChannelHandler extends SimpleChannelInboundHandler<HttpObject>
{
    private static final Logger logger = LoggerFactory.getLogger(ChunkChannelHandler.class);

    /**
     * Http数据工厂
     */
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    /**
     * Http POST请求解码器
     */
    private HttpPostRequestDecoder decoder;

    /**
     * 响应内容
     */
    private final StringBuilder responseContent = new StringBuilder();

    /**
     * Http请求
     */
    private HttpRequest request;

    /**
     * 是否正在读取Chunk
     */
    private boolean readingChunks;

    /**
     * 处理接受到的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception
    {
        logger.info("Message received and class = " + msg.getClass().getName());

        // 解析HttpRequest
        if (msg instanceof HttpRequest)
        {
            HttpRequest request = this.request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            logger.info("Request uri==" + uri.getPath());

            // 场景1 忽略浏览器请求favicon.ico的请求
            if ("/favicon.ico".equals(uri.getPath()))
            {
                return;
            }

            // 场景2 根环境则构建表单
            if ("/".equals(uri.getPath()))
            {
                buildForms(ctx);
                return;
            }

            // 场景3 构建表单提交的结果
            // HTTP.GET请求
            if (HttpMethod.GET.equals(request.method()))
            {
                logger.info("Http.GET");

                buildGetResponse();
                writeResponse(ctx.channel());
                return;
            }

            // HTTP.POST请求
            if (HttpMethod.POST.equals(request.method()))
            {
                logger.info("Http.POST");

                decoder = new HttpPostRequestDecoder(factory, request);
                readingChunks = HttpUtil.isTransferEncodingChunked(request);
                responseContent.append("Is Chunked: " + readingChunks + "\r\n");
                responseContent.append("IsMultipart: " + decoder.isMultipart() + "\r\n");
                if (readingChunks)
                {
                    responseContent.append("Chunks: ");
                    readingChunks = true;
                }
            }
        }

        if (decoder != null)
        {
            if (msg instanceof HttpContent)
            {
                HttpContent chunk = (HttpContent) msg;
                decoder.offer(chunk);
                try
                {
                    while (decoder.hasNext())
                    {
                        InterfaceHttpData data = decoder.next();
                        if (data != null)
                        {
                            try
                            {
                                buildHttpData(data);
                            }
                            finally
                            {
                                data.release();
                            }
                        }
                    }
                }
                catch (HttpPostRequestDecoder.EndOfDataDecoderException e1)
                {
                    responseContent.append("\r\nEND OF CONTENT CHUNK BY CHUNK\r\n");
                }

                // example of reading only if at the end
                if (chunk instanceof LastHttpContent)
                {
                    writeResponse(ctx.channel());
                    readingChunks = false;
                    reset();
                }
            }
        }
    }

    /**
     * 构建HttpPost数据
     *
     * @param data
     */
    private void buildHttpData(InterfaceHttpData data)
    {
        /**
         * HttpDataType有三种类型
         * Attribute, FileUpload, InternalAttribute
         */
        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute)
        {
            Attribute attribute = (Attribute) data;
            String value;
            try
            {
                value = attribute.getValue();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType()
                        .name() + ":" + attribute.getName() + " Error while reading value: " + e1.getMessage() + "\r\n");
                return;
            }
            if (value.length() > 100)
            {
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType()
                        .name() + ":" + attribute.getName() + " data too long\r\n");
            }
            else
            {
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType()
                        .name() + ":" + attribute.toString() + "\r\n");
            }
        }
    }

    /**
     * 返回Response
     *
     * @param channel
     */
    private void writeResponse(Channel channel)
    {
        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        if (HttpUtil.isKeepAlive(request))
        {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        ChannelFuture future = channel.writeAndFlush(response);
        future.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 构建Http.GET提交的响应
     */
    private void buildGetResponse()
    {
        buildCommonResponse();
        responseContent.append("\r\nEND OF GET CONTENT\r\n");
    }

    /**
     * 构建公共响应部分
     */
    private void buildCommonResponse()
    {
        responseContent.setLength(0);
        responseContent.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
        responseContent.append("===================================\r\n");
        responseContent.append("VERSION: " + request.protocolVersion().text() + "\r\n");
        responseContent.append("REQUEST_URI: " + request.uri() + "\r\n\r\n");

        // 输出HttpRequest的Header属性
        for (Map.Entry<String, String> entry : request.headers())
        {
            responseContent.append("HEADER: " + entry.getKey() + '=' + entry.getValue() + "\r\n");
        }

        // 输出URI参数
        QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri());
        Map<String, List<String>> uriAttributes = decoderQuery.parameters();
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet())
        {
            for (String attrVal : attr.getValue())
            {
                responseContent.append("URI: " + attr.getKey() + '=' + attrVal + "\r\n");
            }
        }
    }

    /**
     * 构建表单
     *
     * @param ctx
     */
    private void buildForms(ChannelHandlerContext ctx)
    {
        // 清空
        responseContent.setLength(0);

        // 构建表单
        responseContent.append("<html>");
        responseContent.append("<head>");
        responseContent.append("<title>Netty Test Form</title>\r\n");
        responseContent.append("</head>\r\n");
        responseContent.append("<body bgcolor=white><style>td{font-size: 12pt;}</style>");

        responseContent.append("<table border=\"0\">");
        responseContent.append("<tr>");
        responseContent.append("<td>");
        responseContent.append("<h1>Netty Test Form</h1>");
        responseContent.append("Choose one FORM");
        responseContent.append("</td>");
        responseContent.append("</tr>");
        responseContent.append("</table>\r\n");

        // GET表单
        responseContent.append("<CENTER>GET FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
        responseContent.append("<FORM ACTION=\"/from-get\" METHOD=\"GET\">");
        responseContent.append("<input type=hidden name=getform value=\"GET\">");
        responseContent.append("<table border=\"0\">");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"GetForm-input1\" size=10></td></tr>");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"GetForm-input2\" size=10></td></tr>");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"GetForm-input3\" size=20>");
        responseContent.append("<tr><td>Fill with value: <br> <textarea name=\"GetForm-input4\" cols=40 rows=10></textarea>");
        responseContent.append("</td></tr>");
        responseContent.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
        responseContent.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
        responseContent.append("</table></FORM>\r\n");
        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");

        // POST表单
        responseContent.append("<CENTER>POST FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
        responseContent.append("<FORM ACTION=\"/from-post\" METHOD=\"POST\">");
        responseContent.append("<input type=hidden name=getform value=\"POST\">");
        responseContent.append("<table border=\"0\">");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"PostForm-Input1\" size=10></td></tr>");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"PostForm-Input2\" size=20>");
        responseContent.append("<tr><td>Fill with value: <br> <textarea name=\"PostForm-Input3\" cols=40 rows=10></textarea>");
        responseContent.append("<tr><td>Fill with file (only file name will be transmitted): <br> " + "<input type=file name=\"PostForm-file\">");
        responseContent.append("</td></tr>");
        responseContent.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
        responseContent.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
        responseContent.append("</table></FORM>\r\n");
        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
        responseContent.append("</body>");
        responseContent.append("</html>");
        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
        ctx.channel().writeAndFlush(response);
    }

    /**
     * 重置请求
     */
    private void reset()
    {
        request = null;
        decoder.destroy(); // destroy the decoder to release all resources
        decoder = null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception
    {
        messageReceived(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        if (decoder != null)
        {
            decoder.cleanFiles();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.error("Handler HTTP meet error.", cause);
        ctx.channel().close();
    }
}
