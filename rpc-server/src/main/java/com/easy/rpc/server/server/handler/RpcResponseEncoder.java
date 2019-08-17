package com.easy.rpc.server.server.handler;

import com.easy.rpc.common.entity.RpcResponse;
import com.easy.rpc.serialization.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ljl
 * @since 2019/8/17
 */
public class RpcResponseEncoder extends MessageToByteEncoder<RpcResponse> {

    private Serializer serializer;

    public RpcResponseEncoder(Serializer serializer){
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse msg, ByteBuf out) throws Exception {
        byte[] bytes = serializer.serialize(msg);
        if (bytes == null){
            return;
        }
        int lenth = bytes.length;
        out.writeInt(lenth);
        out.writeBytes(bytes);
    }
}
