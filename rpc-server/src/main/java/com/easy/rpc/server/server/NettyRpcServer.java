package com.easy.rpc.server.server;

import com.easy.rpc.common.entity.RpcRequest;
import com.easy.rpc.serialization.deserialize.Deserializer;
import com.easy.rpc.serialization.deserialize.JsonDeserializer;
import com.easy.rpc.server.server.handler.RpcHandler;
import com.easy.rpc.server.server.handler.RpcRequestDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;

/**
 * @author ljl
 * @since 2019/8/16
 */
public class NettyRpcServer {

    private static final Deserializer deserializer = new JsonDeserializer();

    private String ip;
    private int port;
    private HashMap<String, Object> serviceMap;

    public NettyRpcServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public NettyRpcServer(String ip, int port, HashMap<String, Object> serviceMap) {
        this.ip = ip;
        this.port = port;
        this.serviceMap = serviceMap;
    }

    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(ip, port)
                .childHandler(new NettyRpcServerInitializer());
    }

    /**
     * channel initiallizer，用于初始化channelPipeline的channelHandler链，初始化之后该initiallizer会自动从channelPipeline卸载
     */
    class NettyRpcServerInitializer extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new RpcRequestDecoder(deserializer, RpcRequest.class));
            pipeline.addLast(new RpcHandler(serviceMap));
            //outBound handler，进行编码
        }
    }

}
