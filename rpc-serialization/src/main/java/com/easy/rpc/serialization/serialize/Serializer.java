package com.easy.rpc.serialization.serialize;

/**
 * @author ljl
 * @since 2019/8/17
 */
public interface Serializer {

    byte[] serialize(Object o);

}
