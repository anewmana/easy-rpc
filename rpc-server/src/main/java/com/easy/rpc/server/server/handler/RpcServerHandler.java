package com.easy.rpc.server.server.handler;

import com.easy.rpc.common.ServiceNameConverter;
import com.easy.rpc.common.entity.RpcRequest;
import com.easy.rpc.common.entity.RpcResponse;
import com.easy.rpc.common.exception.RpcException;
import com.easy.rpc.common.utils.CollectionUtils;
import io.netty.channel.*;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class RpcServerHandler extends ChannelInboundHandlerAdapter{

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

    private HashMap<String, Object> serviceMap;
    public RpcServerHandler(HashMap<String, Object> serviceMap){
        this.serviceMap = serviceMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!RpcRequest.class.isInstance(msg)){
            return;
        }
        RpcResponse rpcResponse = new RpcResponse();
        Object result = null;
        RpcRequest request = (RpcRequest)msg;
        try {
            result = handle(request);
        }catch (InvocationTargetException ite){
            LOGGER.error("handle RpcRequest exception,", ite);
            rpcResponse.setException(ite);
        }catch (Exception e){
            LOGGER.error("handle RpcRequest exception,", e);
            ctx.close().sync();
            return;
        }
        rpcResponse.setResult(result);
        rpcResponse.setRpcUniqueId(request.getRpcUniqueId());
        //调用channel的writeAndFlush方法，使IO事件从hanler链尾开始流动，所有的outBound方向的handler都能处理
        //响应回传后关闭连接
        ctx.channel().writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 处理请求
     * @param msg
     * @return
     * @throws Exception
     */
    private Object handle(RpcRequest msg) throws Exception {
        if(msg == null){
            return null;
        }
        if(CollectionUtils.isEmpty(serviceMap)){
            throw new RpcException("service map is empty");
        }
        String fullServiceName = ServiceNameConverter.getFullServiceName(msg.getServiceName(), msg.getVersion());
        Object serviceBean = serviceMap.get(fullServiceName);
        if(serviceBean == null){
            throw new RpcException(String.format("can not find service bean by serviceName:%s", fullServiceName));
        }
        //反射调用
        Class cls = Class.forName(msg.getServiceName());
        FastClass fastClass = FastClass.create(cls);
        String methodName = msg.getMethodName();
        Class<?>[] paramTypes = msg.getParameterTypes();
        Object[] params = msg.getParameters();
        FastMethod fastMethod = fastClass.getMethod(methodName, paramTypes);

        Object o = fastMethod.invoke(serviceBean, params);
        return o;
    }
}
