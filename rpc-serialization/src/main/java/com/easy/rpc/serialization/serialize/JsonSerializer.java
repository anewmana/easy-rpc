package com.easy.rpc.serialization.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author ljl
 * @since 2019/8/17
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object o) {
        return JSON.toJSONBytes(o, SerializerFeature.WriteEnumUsingToString);
    }
}
