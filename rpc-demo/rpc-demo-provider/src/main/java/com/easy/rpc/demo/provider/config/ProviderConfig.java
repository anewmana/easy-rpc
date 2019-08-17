package com.easy.rpc.demo.provider.config;

import com.easy.rpc.server.RpcServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ljl
 * @since 2019/8/17
 */
@Configuration
@ComponentScan(basePackages = "com.easy.rpc.demo.provider")
@PropertySource(value = {"classpath:/spring/spring.properties", "classpath:/spring/log4j.properties"})
public class ProviderConfig {

    @Bean(name = "rpcServer")
    public RpcServer rpcServer(@Value("${rpc.server.ip}") String ip, @Value("${rpc.server.port}") int port){
        return new RpcServer(ip, port);
    }

}
