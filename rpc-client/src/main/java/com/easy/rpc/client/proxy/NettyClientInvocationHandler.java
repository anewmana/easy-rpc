package com.easy.rpc.client.proxy;

import com.easy.rpc.client.RpcNewClient;
import com.easy.rpc.common.ServiceNameConverter;
import com.easy.rpc.common.entity.RpcRequest;
import com.easy.rpc.common.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class NettyClientInvocationHandler implements InvocationHandler {

    public NettyClientInvocationHandler(Class cls, String version) {
        this.version = version;
        this.cls = cls;
    }

    private String version;
    private Class cls;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = buildRpcRequest(cls, method, args);
        RpcNewClient rpcClient = new RpcNewClient();
        try {
            RpcResponse rpcResponse = rpcClient.invoke(request);
            if(rpcResponse != null){
                return rpcResponse.getResult();
            }
            return null;
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException");
            throw e.getCause();
        }
    }

    private RpcRequest buildRpcRequest(Class cls, Method method, Object[] args){
        String serviceName = cls.getName();
        String methodName = method.getName();
        Class[] parameterTypes = method.getParameterTypes();
        return buildRpcRequest(serviceName, version, methodName, parameterTypes, args);
    }

    private RpcRequest buildRpcRequest(String serviceName, String version, String methodName, Class[] parameterTypes, Object[] args){
        String uuid = UUID.randomUUID().toString();
        RpcRequest request = new RpcRequest();
        request.setServiceName(serviceName);
        request.setVersion(version);
        request.setRpcUniqueId(uuid);
        request.setMethodName(methodName);
        request.setParameters(args);
        request.setParameterTypes(parameterTypes);
        return request;
    }

}