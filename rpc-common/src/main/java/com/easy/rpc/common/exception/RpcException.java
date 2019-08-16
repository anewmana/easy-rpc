package com.easy.rpc.common.exception;

/**
 * @author ljl
 * @since 2019/8/17
 */
public class RpcException extends RuntimeException{

    public RpcException(String message){
        super(message);
    }

}
