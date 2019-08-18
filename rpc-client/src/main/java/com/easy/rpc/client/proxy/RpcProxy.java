package com.easy.rpc.client.proxy;

/**
 * @author ljl
 * @since 2019/8/18
 */
public interface RpcProxy extends Proxy {

    /**
     * 创建指定接口的指定version的代理类
     * @param cls
     * @param version
     * @param <T>
     * @return
     */
    <T> T createProxy(Class<T> cls, String version);

}
