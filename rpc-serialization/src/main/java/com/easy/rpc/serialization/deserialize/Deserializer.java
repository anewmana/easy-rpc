package com.easy.rpc.serialization.deserialize;

public interface Deserializer {

    /**
     * 字节数组反序列化
     * @param bytes
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> cls);

}
