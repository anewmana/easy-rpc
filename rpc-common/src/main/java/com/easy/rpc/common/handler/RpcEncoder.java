package com.easy.rpc.common.handler;

import com.easy.rpc.serialization.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ljl
 * @since 2019/8/17
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Serializer serializer;
    private Class cls;

    public RpcEncoder(Serializer serializer, Class cls){
        this.serializer = serializer;
        this.cls = cls;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(cls.isInstance(msg)){
            byte[] bytes = serializer.serialize(msg);
            if (bytes == null){
                return;
            }
            int lenth = bytes.length;
            out.writeInt(lenth);
            out.writeBytes(bytes);
        }
    }
}
