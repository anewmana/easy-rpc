package com.easy.rpc.client.proxy;

/**
 * @author ljl
 * @since 2019/8/18
 */
public interface Proxy {

    /**
     * 创建指定接口的代理类
     * @param cls
     * @param <T>
     * @return
     */
    <T> T createProxy(Class<T> cls);

}
