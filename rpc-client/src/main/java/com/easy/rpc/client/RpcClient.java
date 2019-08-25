package com.easy.rpc.client;

import com.easy.rpc.client.handler.RpcCientHandler;
import com.easy.rpc.common.entity.RpcRequest;
import com.easy.rpc.common.entity.RpcResponse;
import com.easy.rpc.common.handler.RpcDecoder;
import com.easy.rpc.common.handler.RpcEncoder;
import com.easy.rpc.serialization.deserialize.Deserializer;
import com.easy.rpc.serialization.deserialize.JsonDeserializer;
import com.easy.rpc.serialization.serialize.JsonSerializer;
import com.easy.rpc.serialization.serialize.Serializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class RpcClient{

    private static final Deserializer DESERIALIZER = new JsonDeserializer();
    private static final Serializer SERIALIZER = new JsonSerializer();

    private String remoteIp = "localhost";
    private int remotePort = 8090;

    public RpcResponse invoke(RpcRequest request) throws Exception{
        if (request == null){
            throw new IllegalArgumentException("request cannot be null");
        }
        return request(request);
    }

    private RpcResponse request(RpcRequest request)throws Exception{
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(remoteIp, remotePort)
                .handler(new RpcClientInitializer());

        ChannelFuture future = bootstrap.connect().sync();
        ChannelFuture writeFuture = future.channel().writeAndFlush(request);
        RpcListener listener = new DefaultRpcListener();
        RpcHolder.addListener(request.getRpcUniqueId(), listener);
        RpcResponse rpcResponse = (RpcResponse) listener.get();
        RpcHolder.removeListener(request.getRpcUniqueId());
        writeFuture.channel().closeFuture().sync();
        close(eventLoopGroup);
        return rpcResponse;
    }

    private void close(EventLoopGroup eventLoopGroup){
        eventLoopGroup.shutdownGracefully();
    }


    class RpcClientInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new RpcDecoder(DESERIALIZER, RpcResponse.class));
            pipeline.addLast(new RpcEncoder(SERIALIZER, RpcRequest.class));
            pipeline.addLast(new RpcCientHandler());
        }
    }

}
