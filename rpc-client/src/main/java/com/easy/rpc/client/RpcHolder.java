package com.easy.rpc.client;

import com.easy.rpc.common.entity.RpcResponse;
import com.easy.rpc.common.utils.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class RpcHolder {

    private static final ConcurrentHashMap<String, RpcResponse> responseMap = new ConcurrentHashMap<>();


    public static RpcResponse getResponse(String id){
        return responseMap.get(id);
    }

    public static void setResponse(RpcResponse response){
        if(response == null || StringUtils.isBlank(response.getRpcUniqueId())){
            return;
        }
        responseMap.put(response.getRpcUniqueId(), response);
    }

}
