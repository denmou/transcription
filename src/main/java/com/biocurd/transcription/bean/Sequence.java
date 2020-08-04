package com.biocurd.transcription.bean;

import com.biocurd.transcription.exception.ExpressException;

import java.util.Stack;
import java.util.function.Function;

/**
 * 转录序列
 * 存储数据栈以及操作栈信息
 *
 * @author denmou
 * @date 2020/8/3 09:21
 */
public class Sequence {
    private final StringBuilder builder;
    private final Stack<Character> operationStack;
    private final Stack<String> dataStack;
    private final Functions functions;

    public Sequence() {
        builder = new StringBuilder();
        operationStack = new Stack<>();
        dataStack = new Stack<>();
        functions = new Functions();
    }

    public void addValue(Object value) {
        builder.append(value);
    }

    private String getValue() {
        String value = builder.toString();
        builder.setLength(0);
        return value;
    }

    public Function<String, Object> getFunction(String type) {
        return functions.get(type);
    }

    public Object getParam() throws ExpressException {
        String value = dataStack.pop();
        String type = dataStack.pop();
        Function<String, Object> function = this.getFunction(type);
        if (function == null) {
            throw new ExpressException("未知基础类型[" + type + "]");
        } else {
            return function.apply(value);
        }
    }

    public void addData() {
        if (builder.length() > 0) {
            dataStack.push(getValue());
        }
    }

    public void addOperation(Character value) {
        operationStack.push(value);
    }

    public String dataPop() {
        return dataStack.pop();
    }

    public Character operationPop() {
        return operationStack.pop();
    }

    public boolean complete() {
        return operationStack.isEmpty();
    }

    public String export() {
        while (!dataStack.isEmpty()) {
            builder.insert(0, dataStack.pop());
        }
        return builder.toString();
    }
}
