package com.easy.rpc.client.handler;

import com.easy.rpc.client.RpcHolder;
import com.easy.rpc.client.RpcListener;
import com.easy.rpc.common.entity.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class RpcCientHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof RpcResponse){
            RpcResponse response = (RpcResponse) msg;
            String rpcUniqueId = response.getRpcUniqueId();
            RpcListener rpcListener = RpcHolder.getListener(rpcUniqueId);
            rpcListener.notifyWithValue(response);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.channel().close().sync();
    }
}
