package com.biocurd.transcription.bean;

/**
 * 模版链
 *
 * @author denmou
 * @date 2020/8/2 23:52
 */
public class TemplateStrand {
    private int index;
    private final int length;
    private final String template;

    public TemplateStrand(String template) {
        this.index = -1;
        this.template = template;
        this.length = template.length() - 1;
    }

    public boolean hasNext() {
        return index < length;
    }

    public char next() {
        return template.charAt(++index);
    }

    public char get() {
        return template.charAt(index);
    }
}
