package com.shein.server;

import com.shein.server.handler.MyChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by dell on 2018/11/16.
 */
@Component
public class WebSocketServer {

    @Value("${socket.server.port}")
    private int port;

    @Value("${socket.server.address}")
    private String address;


    public void start() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            sb.group(group, bossGroup) // 绑定线程池
                    .channel(NioServerSocketChannel.class) // 指定使用的channel
                    .localAddress(this.port)// 绑定监听端口
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("收到新连接");
                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            ch.pipeline().addLast("http-codec",new HttpServerCodec());
                            //以块的方式来写的处理器
                            ch.pipeline().addLast("aggregator",new HttpObjectAggregator(8192));
                            ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());

//                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));

                            ch.pipeline().addLast(new MyChannelHandler());

                        }
                    });
            ChannelFuture cf = sb.bind(address,port).sync(); // 服务器异步创建绑定
            System.out.println(WebSocketServer.class + " 启动正在监听： " + cf.channel().localAddress());
            cf.channel().closeFuture().sync(); // 关闭服务器通道
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
            bossGroup.shutdownGracefully().sync();
        }
    }


}
