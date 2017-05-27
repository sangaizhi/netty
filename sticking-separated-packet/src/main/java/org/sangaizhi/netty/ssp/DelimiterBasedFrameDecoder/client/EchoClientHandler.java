package org.sangaizhi.netty.ssp.DelimiterBasedFrameDecoder.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author sangaizhi
 * @date 2017/5/27
 */
public class EchoClientHandler extends SimpleChannelInboundHandler {

	private int counter;

	private static final String REQ = "Hi, Welcome to Netty.}}";

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	    for(int i=0;i<100;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(REQ.getBytes()));
        }
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("this is " + ++counter + " times receive server:[" + msg + "}");
	}
}
