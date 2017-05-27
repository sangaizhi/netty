package org.sangaizhi.netty.ssp.LineBasedFrameDecoder.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author sangaizhi
 * @date 2017/5/27
 */
public class TimeServer {
    public void bind(int port){

        //  boss 线程池，管理 accept 事件
        EventLoopGroup boos = new NioEventLoopGroup();
        // worker 线程池，管理各 channel 的IO事件
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 注册 boss 和 worker 线程池
            bootstrap.group(boos, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG,2048);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 添加以回车换行符为结束标志的解码器
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    // 添加将字节转换为对象的解码器
                    ch.pipeline().addLast(new StringDecoder());
                    // 绑定事件处理器
                    ch.pipeline().addLast(new TimeServerHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
           // channelFuture.channel().close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //boos.shutdownGracefully();
            //worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        new TimeServer().bind(port);
    }
}
