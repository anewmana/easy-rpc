package com.easy.rpc.common;

import com.easy.rpc.common.utils.StringUtils;

/**
 * @author ljl
 * @since 2019/8/16
 */
public class ServiceNameConverter {

    /**
     * 根据接口名和版本号获取服务的全名
     * @param serviceName 服务名
     * @param version 版本号
     * @return 服务全名
     */
    public static String getFullServiceName(String serviceName, String version){
        if(StringUtils.isBlank(serviceName)){
            throw new NullPointerException("interfaceName can not be null");
        }
        version = (version == null) ? "" : version;
        return serviceName + version;
    }

}
