package com.biocurd.transcription.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.Function;

/**
 * 将数据转化为指定类型方法集
 *
 * @author denmou
 * @date 2020/8/3 16,10
 */
public class Functions extends HashMap<String, Function<String, Object>> {
    public Functions() {
        put("byte", Byte::parseByte);
        put("short", Short::parseShort);
        put("int", Integer::parseInt);
        put("decimal", BigDecimal::new);
        put("float", Float::parseFloat);
        put("long", Long::parseLong);
        put("double", Double::parseDouble);
        put("char", e -> e.charAt(0));
        put("string", e -> e);
        put("boolean", Boolean::parseBoolean);
    }
}
