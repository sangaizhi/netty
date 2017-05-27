package org.sangaizhi.netty.ssp.LineBasedFrameDecoder.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sangaizhi
 * @date 2017/5/27
 */
public class TimeClientHandler extends SimpleChannelInboundHandler {

    private Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    private int counter;
    private byte[] req;

    TimeClientHandler(){
        req = ("QUERY ORDER TIME" + System.getProperty("line.separator")).getBytes();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for(int i=0;i < 100;i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Now is :" + body + "; the counter is :" + ++counter);
    }
}
