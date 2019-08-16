package com.easy.rpc.common.entity;

public class RpcResponse {

    /**
     * rpc唯一id
     */
    private String rpcUniqueId;

    /**
     * 异常
     */
    private Exception exception;

    /**
     * rpc结果
     */
    private Object result;

    public String getRpcUniqueId() {
        return rpcUniqueId;
    }

    public void setRpcUniqueId(String rpcUniqueId) {
        this.rpcUniqueId = rpcUniqueId;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
