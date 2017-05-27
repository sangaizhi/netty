package org.sangaizhi.netty.ssp.LineBasedFrameDecoder.server;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author sangaizhi
 * @date 2017/5/27
 */
public class TimeServerHandler extends SimpleChannelInboundHandler {

	private int counter;

	@Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
	    // 由于添加了 LineBasedFrameDecoder 解码器，所以此处接收到的消息都是删除了回车换行后的请求消息
        // 不需要考虑半包问题，也不需要对请求消息进行编码
        String body = (String) msg;
        System.out.println("The time server receive order  :" + body + ": the counter is :" + ++counter);
        String currentTime = "QUERY ORDER TIME".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD REQUEST";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        channelHandlerContext.writeAndFlush(resp);
	}

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
