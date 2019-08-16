package com.easy.rpc.server;

import com.easy.rpc.common.ServiceNameConverter;
import com.easy.rpc.common.utils.CollectionUtils;
import com.easy.rpc.common.utils.StringUtils;
import com.easy.rpc.server.annotion.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

 /**
 * @author ljl
 * @since 2019/8/16
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    /**
     * 服务端ip
     */
    private String ip;

    /**
     * 服务端监听端口
     */
    private int port;

    public RpcServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * 服务名和bean实例的映射
     */
    private HashMap<String, Object> serviceMap = new HashMap<>(8);

    /**
     * bean属性设置之后，开启监听
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //开始监听
        if(StringUtils.isBlank(ip)){
            throw new IllegalArgumentException("ip can not be null");
        }

    }

    /**
     * 扫描上下文中加了指定注解的bean，维护服务名和实例映射
     * @param ctx 应用上下文
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> beanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if(CollectionUtils.isEmpty(beanMap)){
            return;
        }
        for(Object serviceBean : beanMap.values()){
            RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
            String serviceName = rpcService.value().getName();
            String version = rpcService.version();
            String fullServiceName = ServiceNameConverter.getFullServiceName(serviceName, version);
            serviceMap.put(fullServiceName, serviceBean);
        }
    }
}
