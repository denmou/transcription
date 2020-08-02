package com.biocurd.transcription;

import com.biocurd.transcription.annotation.Polymerase;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据转录器
 *
 * @author denmou
 * @date 2020/8/1 23:35
 */
public class Translator {
    private Map<String, Object> polymerases;

    public Translator(Object... polymerases) {
        this.polymerases = new HashMap<>(8);
        for (Object polymerase : polymerases) {
            Class<?> clazz = polymerase.getClass();
            Polymerase annotation = clazz.getAnnotation(Polymerase.class);
            this.polymerases.put(annotation != null ? annotation.name() : clazz.getSimpleName(), polymerase);
        }
    }

    public String execute(String template) {
        for (int i = 0; i < template.length(); i++) {
            char c = template.charAt(i);
        }
        return "";
    }
}
