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
