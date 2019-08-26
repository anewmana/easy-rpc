package com.easy.rpc.registry;

/**
 * @author ljl
 * @since 2019/8/27
 */
public interface ServiceRegistry {

    /**
     * 注册服务提供者地址和服务名
     * @param serviceName   服务名
     * @param serviceAddress   服务地址，包含ip和端口
     */
    void register(String serviceName, String serviceAddress);

}
