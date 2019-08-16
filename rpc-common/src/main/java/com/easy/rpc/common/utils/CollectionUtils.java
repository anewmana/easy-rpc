package com.easy.rpc.common.utils;

import java.util.Map;

/**
 * @author ljl
 * @since 2019/8/16
 */
public class CollectionUtils {

    public static boolean isEmpty(Map map){
        return (map == null) || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map){
        return !isEmpty(map);
    }

}
