package com.biocurd.transcription.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.Function;

/**
 * 将数组转换指定类型数组的方法集
 *
 * @author denmou
 * @date 2020/8/3 16:36
 */
public class ArrayFunctions extends HashMap<String, Function<Object[], Object[]>> {
    public ArrayFunctions() {
        put("byte", v -> {
            Byte[] n = new Byte[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = Byte.parseByte(v[i].toString());
            }
            return n;
        });
        put("short", v -> {
            Short[] n = new Short[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = Short.parseShort(v[i].toString());
            }
            return n;
        });
        put("int", v -> {
            Integer[] n = new Integer[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = Integer.parseInt(v[i].toString());
            }
            return n;
        });
        put("decimal", v -> {
            BigDecimal[] n = new BigDecimal[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = new BigDecimal(v[i].toString());
            }
            return n;
        });
        put("float", v -> {
            Float[] n = new Float[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = Float.parseFloat(v[i].toString());
            }
            return n;
        });
        put("long", v -> {
            Long[] n = new Long[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = Long.parseLong(v[i].toString());
            }
            return n;
        });
        put("double", v -> {
            Double[] n = new Double[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = Double.parseDouble(v[i].toString());
            }
            return n;
        });
        put("char", v -> {
            Character[] n = new Character[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = v[i].toString().charAt(0);
            }
            return n;
        });
        put("string", v -> {
            String[] n = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = v[i].toString();
            }
            return n;
        });
        put("boolean", v -> {
            Boolean[] n = new Boolean[v.length];
            for (int i = 0; i < v.length; i++) {
                n[i] = Boolean.parseBoolean(v[i].toString());
            }
            return n;
        });
    }
}
