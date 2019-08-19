package com.easy.rpc.demo.comsumer;

import com.easy.rpc.client.proxy.NettyClientProxy;
import com.easy.rpc.demo.api.HelloService;

/**
 * @author ljl
 * @since 2019/8/19
 */
public class HelloDemo {
    public static void main(String[] args) {
        NettyClientProxy proxy = new NettyClientProxy();
        HelloService helloService = proxy.createProxy(HelloService.class, "0.1");
        String result = helloService.hello("easy-rpc");
        System.out.println(result);
    }

}
