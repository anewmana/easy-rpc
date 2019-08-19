package com.easy.rpc.client;

import com.easy.rpc.common.entity.RpcResponse;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class RpcHolder {
    private static RpcResponse rpcResponse;

    public static RpcResponse getResponse(){
        return rpcResponse;
    }

    public static void setResponse(RpcResponse response){
        rpcResponse = response;
    }

}
