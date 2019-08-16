package com.easy.rpc.common.entity;

public class RpcRequest {

    /**
     * 唯一标识一次rpc
     */
    private String rpcUniqueId;
    /**
     * 服务名
     */
    private String serviceName;
    /**
     * 服务版本，可为空
     */
    private String version;

    /**
     * 调用方法名
     */
    private String methodName;
    /**
     * 方法参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 方法参数数组
     */
    private Object[] parameters;

    public String getRpcUniqueId() {
        return rpcUniqueId;
    }

    public void setRpcUniqueId(String rpcUniqueId) {
        this.rpcUniqueId = rpcUniqueId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
