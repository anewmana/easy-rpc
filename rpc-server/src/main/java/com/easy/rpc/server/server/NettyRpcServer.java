package com.easy.rpc.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ljl
 * @since 2019/8/16
 */
public class NettyServer {

    private String ip;
    private int port;

    public NettyServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(ip, port)
                .childHandler();
    }

    ChannelInitializer<>

}
