package com.easy.rpc.demo.provider;

import com.easy.rpc.demo.provider.config.ProviderConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ljl
 * @since 2019/8/17
 */
public class ProviderBootstrap {

    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfig.class);
        context.start();
        System.in.read();
    }

}
