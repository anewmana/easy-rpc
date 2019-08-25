package com.easy.rpc.client;

/**
 * @author ljl
 * @since 2019/8/25
 */
public interface RpcListener {

    Object get();

    void notifyWithValue(Object o);

}
