package com.easy.rpc.demo.provider.service;

import com.easy.rpc.demo.api.HelloService;
import com.easy.rpc.server.annotion.RpcService;
import org.springframework.stereotype.Component;

/**
 * @author ljl
 * @since 2019/8/17
 */
@RpcService(value = HelloServiceImpl.class, version = "0.1")
@Component
public class HelloServiceImpl implements HelloService{

    @Override
    public String hello(String name) {
        return "hello," + name;
    }
}
