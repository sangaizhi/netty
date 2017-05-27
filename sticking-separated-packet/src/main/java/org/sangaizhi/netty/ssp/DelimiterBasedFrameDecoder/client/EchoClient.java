package org.sangaizhi.netty.ssp.DelimiterBasedFrameDecoder.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author sangaizhi
 * @date 2017/5/27
 */
public class EchoClient {

	public void connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		try {
			bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {

					protected void initChannel(SocketChannel ch) throws Exception {
						ByteBuf byteBuf = Unpooled.copiedBuffer("}}".getBytes());
						ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new EchoClientHandler());
					}
				});
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			//channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
		} finally {
			//group.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		new EchoClient().connect("127.0.0.1", 8080);
	}
}
