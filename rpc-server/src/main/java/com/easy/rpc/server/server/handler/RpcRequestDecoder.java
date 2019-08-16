package com.easy.rpc.server.server.handler;

import com.easy.rpc.serialization.deserialize.Deserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static com.easy.rpc.common.constant.RpcConstant.DATA_LENGTH_IN_BYTE;

public class RpcRequestDecoder extends ByteToMessageDecoder {

    public RpcRequestDecoder(Deserializer deserializer, Class cls) {
        this.deserializer = deserializer;
        this.cls = cls;
    }
    private Deserializer deserializer;
    private Class cls;

    @SuppressWarnings("unchecked")
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < DATA_LENGTH_IN_BYTE ){
            return;
        }
        //标记读索引
        in.markReaderIndex();
        int dataLength = in.readInt();
        if(in.readableBytes() < dataLength){
            //数据未读完，reset
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[dataLength];
        in.readBytes(bytes);
        //反序列化得到request对象
        out.add(deserializer.deserialize(bytes, cls));
    }
}
