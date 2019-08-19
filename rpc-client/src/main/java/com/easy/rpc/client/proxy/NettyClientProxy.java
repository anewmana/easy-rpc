package com.easy.rpc.client.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

/**
 * @author ljl
 * @since 2019/8/18
 */
public class NettyClientProxy implements RpcProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientProxy.class);

    @Override
    public <T> T createProxy(Class<?> cls) {
        return createProxy(cls, "");
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T createProxy(Class<?> cls, String version) {
        version = (version == null) ? "" : version;
        return (T)Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new NettyClientInvocationHandler(cls, version));
    }

}
