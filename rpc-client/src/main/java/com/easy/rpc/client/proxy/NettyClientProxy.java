package com.easy.rpc.client.proxy;

import com.easy.rpc.common.ServiceNameConverter;
import com.easy.rpc.common.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author ljl
 * @since 2019/8/18
 */
public class NettyClientProxy implements RpcProxy {


    @Override
    public <T> T createProxy(Class<T> cls) {
        return createProxy(cls, "");
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T createProxy(Class<T> cls, String version) {
        version = (version == null) ? "" : version;
        return (T)Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new NettyClientInvocationHandler(cls, version));

    }

    class NettyClientInvocationHandler implements InvocationHandler{
        public NettyClientInvocationHandler(Class cls, String version) {
            this.version = version;
            this.cls = cls;
        }

        private String version;
        private Class cls;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest request = buildRpcRequest(cls, method, args);

            return null;
        }

        private RpcRequest buildRpcRequest(Class cls, Method method, Object[] args){
            String serviceName = ServiceNameConverter.getFullServiceName(cls, version);
            String methodName = method.getName();
            Class[] parameterTypes = method.getParameterTypes();
            return buildRpcRequest(serviceName, methodName, parameterTypes, args);
        }

        private RpcRequest buildRpcRequest(String serviceName, String methodName, Class[] parameterTypes, Object[] args){
            String uuid = UUID.randomUUID().toString();
            RpcRequest request = new RpcRequest();
            request.setServiceName(serviceName);
            request.setRpcUniqueId(uuid);
            request.setMethodName(methodName);
            request.setParameters(args);
            request.setParameterTypes(parameterTypes);
            return request;
        }
    }


}
