package com.easy.rpc.registry;

/**
 * @author ljl
 * @since 2019/8/27
 */
public interface ServiceRecovery {

    /**
     * 查询指定服务的提供者地址
     * @param serviceName 服务名
     * @return   服务提供者地址
     */
    String recover(String serviceName);

}
