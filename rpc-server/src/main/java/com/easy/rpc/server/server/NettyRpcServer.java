package com.easy.rpc.server.server;

import com.easy.rpc.common.entity.RpcRequest;
import com.easy.rpc.common.entity.RpcResponse;
import com.easy.rpc.common.handler.RpcDecoder;
import com.easy.rpc.common.handler.RpcEncoder;
import com.easy.rpc.serialization.deserialize.Deserializer;
import com.easy.rpc.serialization.deserialize.JsonDeserializer;
import com.easy.rpc.serialization.serialize.JsonSerializer;
import com.easy.rpc.serialization.serialize.Serializer;
import com.easy.rpc.server.server.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author ljl
 * @since 2019/8/16
 */
public class NettyRpcServer {

    private static final Deserializer DESERIALIZER = new JsonDeserializer();
    private static final Serializer SERIALIZER = new JsonSerializer();
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRpcServer.class);

    private String ip;
    private int port;
    private HashMap<String, Object> serviceMap;

    public NettyRpcServer(String ip, int port, HashMap<String, Object> serviceMap) {
        this.ip = ip;
        this.port = port;
        this.serviceMap = serviceMap;
    }

    public void start()throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(ip, port)
                    .childHandler(new NettyRpcServerInitializer());
            ChannelFuture bindFuture = bootstrap.bind(ip, port).sync();
            bindFuture.channel().closeFuture().sync();
        }catch (Exception e){
            LOGGER.error("server start error,", e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * channel initiallizer，用于初始化channelPipeline的channelHandler链，初始化之后该initiallizer会自动从channelPipeline卸载
     */
    class NettyRpcServerInitializer extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new RpcDecoder(DESERIALIZER, RpcRequest.class));
            pipeline.addLast(new RpcServerHandler(serviceMap));
            pipeline.addLast(new RpcEncoder(SERIALIZER, RpcResponse.class));
        }
    }

}
