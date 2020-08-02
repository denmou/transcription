package com.biocurd.transcription.annotation;

import java.lang.annotation.*;

/**
 * 蛋白质名称
 * 数据转录时
 *
 * @author denmou
 * @date 2020/8/1 23:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Polymerase {
    String name();
}
