package com.easy.rpc.client;

import com.easy.rpc.common.entity.RpcRequest;
import com.easy.rpc.common.entity.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class RpcClient {

    private String remoteIp = "localhost";
    private int remotePort = 8080;

    public RpcResponse invoke(RpcRequest request){
        if (request == null){
            throw new IllegalArgumentException("request cannot be null");
        }
        return null;
    }

    private void open()throws Exception{
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(remoteIp, remotePort)
                .handler(new RpcClientInitializer());
        ChannelFuture future = bootstrap.connect().sync();

    }

    class RpcClientInitializer extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
        }
    }

}
