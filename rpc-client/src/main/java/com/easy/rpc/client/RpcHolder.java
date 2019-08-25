package com.easy.rpc.client;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class RpcHolder {
    private static final ConcurrentHashMap<String, RpcListener> responseListenerMap = new ConcurrentHashMap<>();

    public static RpcListener getListener(String rpcUniqueId){
        if(rpcUniqueId == null){
            return null;
        }
        return responseListenerMap.get(rpcUniqueId);
    }

    public static void addListener(String rpcUniqueId, RpcListener rpcListener){
        if(rpcUniqueId == null || rpcListener == null){
            throw new IllegalArgumentException("rpcUniqueId or rpcListener can not be null");
        }
        responseListenerMap.put(rpcUniqueId, rpcListener);
    }

    public static void removeListener(String rpcUniqueId){
        if(rpcUniqueId == null){
            throw new IllegalArgumentException("rpcUniqueId can not be null");
        }
        responseListenerMap.remove(rpcUniqueId);
    }

}
