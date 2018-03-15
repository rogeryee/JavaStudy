package com.yee.study.java.netty.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author Roger.Yi
 */
public class SimpleFileHandler extends SimpleChannelInboundHandler<String>
{
    public static final String CR = System.getProperty("line.separator");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String filePath) throws Exception
    {
        File file = new File(filePath);
        if(!file.exists())
        {
            ctx.writeAndFlush("File not found : " + filePath + CR);
            return;
        }

        if(!file.isFile())
        {
            ctx.writeAndFlush("Not a file : " + filePath + CR);
            return;
        }

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        FileRegion region = new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length());
        ctx.write(region);
        ctx.writeAndFlush(CR);
        randomAccessFile.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
