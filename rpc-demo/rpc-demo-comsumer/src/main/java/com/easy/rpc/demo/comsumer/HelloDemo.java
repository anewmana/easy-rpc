package com.easy.rpc.demo.comsumer;

import com.easy.rpc.client.RpcHolder;
import com.easy.rpc.client.RpcNewClient;
import com.easy.rpc.client.handler.RpcCientHandler;
import com.easy.rpc.client.proxy.NettyClientProxy;
import com.easy.rpc.common.entity.RpcRequest;
import com.easy.rpc.common.entity.RpcResponse;
import com.easy.rpc.common.handler.RpcDecoder;
import com.easy.rpc.common.handler.RpcEncoder;
import com.easy.rpc.demo.api.HelloService;
import com.easy.rpc.serialization.deserialize.Deserializer;
import com.easy.rpc.serialization.deserialize.JsonDeserializer;
import com.easy.rpc.serialization.serialize.JsonSerializer;
import com.easy.rpc.serialization.serialize.Serializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class HelloDemo {
    private static final Deserializer DESERIALIZER = new JsonDeserializer();
    private static final Serializer SERIALIZER = new JsonSerializer();

    private String remoteIp = "localhost";
    private int remotePort = 8090;

    public static void main(String[] args) {
        NettyClientProxy proxy = new NettyClientProxy();
        HelloService helloService = proxy.createProxy(HelloService.class, "0.1");
        String result = helloService.hello("easy-rpc");
        System.out.println(result);
    }

    public static void main1(String[] args) throws Exception {
//        NettyClientProxy proxy = new NettyClientProxy();
//        HelloService helloService = proxy.createProxy(HelloService.class);
//        String result = helloService.hello("easy-rpc");
//        System.out.println(result);
        HelloDemo demo = new HelloDemo();
        String uuid = UUID.randomUUID().toString();
        RpcRequest request = new RpcRequest();
        request.setServiceName(HelloService.class.getName());
        request.setRpcUniqueId(uuid);
        request.setMethodName("hello");
        request.setParameters(new Object[]{"easy-rpc"});
        request.setParameterTypes(new Class[]{String.class});
        request.setVersion("0.1");
        RpcResponse rpcResponse = demo.request(request);
        if(rpcResponse != null){
            System.out.println(rpcResponse.getResult().toString());
        }
    }

    private RpcResponse request(RpcRequest request)throws Exception{
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(remoteIp, remotePort)
                .handler(new RpcClientInitializer())
                .option(ChannelOption.TCP_NODELAY, true);
        ChannelFuture future = bootstrap.connect().sync();
        ChannelFuture writeFuture = future.channel().writeAndFlush(request);
        writeFuture.channel().closeFuture().sync();
        eventLoopGroup.shutdownGracefully().sync();
        RpcResponse rpcResponse = RpcHolder.getResponse();
        return rpcResponse;
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
