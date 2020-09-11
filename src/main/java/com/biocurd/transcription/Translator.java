package com.biocurd.transcription;

import com.biocurd.transcription.annotation.Polymerase;
import com.biocurd.transcription.bean.ArrayFunctions;
import com.biocurd.transcription.bean.Sequence;
import com.biocurd.transcription.bean.TemplateStrand;
import com.biocurd.transcription.constant.Separator;
import com.biocurd.transcription.exception.ExpressException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * 数据转录器
 *
 * @author denmou
 * @date 2020/8/1 23:35
 */
public class Translator {
    private final Map<String, Object> polymerases;
    private final ArrayFunctions functions;

    /**
     * 初始化参与转录的酶实例信息
     *
     * @param polymerases 酶实例组
     */
    public Translator(Object... polymerases) {
        this.polymerases = new HashMap<>(8);
        this.functions = new ArrayFunctions();
        this.addPolymerase(polymerases);
    }

    public void addPolymeraseByName(String name, Object polymerase) {
        this.polymerases.put(name, polymerase);
    }

    public void addPolymerase(Object... polymerases) {
        for (Object polymerase : polymerases) {
            if (polymerase != null) {
                Class<?> clazz = polymerase.getClass();
                Polymerase annotation = clazz.getAnnotation(Polymerase.class);
                this.polymerases.put(annotation != null ? annotation.name() : clazz.getSimpleName(), polymerase);
            }
        }
    }

    /**
     * 执行模版
     *
     * @param template 模版
     * @return 模版输出
     * @throws ExpressException 执行异常信息
     */
    public String execute(String template) throws ExpressException {
        Sequence sequence = new Sequence();
        TemplateStrand strand = new TemplateStrand(template);
        while (strand.hasNext()) {
            char c = strand.next();
            if (c == Separator.START) {
                sequence.addData();
                c = strand.next();
                while (c != Separator.END) {
                    switch (c) {
                        case Separator.PERSIST:
                            sequence.addValue(strand.next());
                            break;
                        case Separator.METHOD:
                        case Separator.PARAM_VALUE:
                        case Separator.PARAM_START:
                        case Separator.ARRAY_START:
                        case Separator.ARRAY_END:
                        case Separator.PARAM_NEXT:
                        case Separator.PARAM_END:
                            sequence.addData();
                            sequence.addOperation(strand.get());
                            break;
                        default:
                            sequence.addValue(c);
                            break;
                    }
                    c = strand.next();
                }
                sequence.addData();
                sequence.addValue(express(sequence));
            } else {
                sequence.addValue(c);
            }
        }
        return sequence.export();
    }

    /**
     * 输出当前表达式操作栈结果
     *
     * @param sequence 转录序列
     * @return 操作栈执行结果
     * @throws ExpressException 执行异常信息
     */
    private String express(Sequence sequence) throws ExpressException {
        Stack<Object> activeStack = new Stack<>();
        Stack<Integer> countStack = new Stack<>();
        int count = 0;
        boolean isArray = false;
        while (!sequence.complete()) {
            switch (sequence.operationPop()) {
                case Separator.PARAM_NEXT:
                    if (isArray) {
                        count++;
                        activeStack.push(sequence.dataPop());
                    }
                    break;
                case Separator.ARRAY_START:
                    count++;
                    Object[] array = new Object[count];
                    array[0] = sequence.dataPop();
                    String type = sequence.dataPop();
                    Function<Object[], Object[]> function = functions.get(type);
                    if (function == null) {
                        throw new ExpressException("未知基础类型[" + type + "]");
                    } else {
                        for (int i = 1; i < count; i++) {
                            array[i] = activeStack.pop();
                        }
                        array = function.apply(array);
                    }
                    activeStack.push(array.getClass());
                    activeStack.push(array);
                    count = countStack.pop() + 1;
                    break;
                case Separator.ARRAY_END:
                    isArray = true;
                case Separator.PARAM_END:
                    countStack.push(count);
                    count = 0;
                    break;
                case Separator.PARAM_VALUE:
                    count++;
                    Object param = sequence.getParam();
                    activeStack.push(param.getClass());
                    activeStack.push(param);
                    break;
                case Separator.METHOD:
                    Object[] params = new Object[count];
                    Class<?>[] classes = new Class[count];
                    for (int i = 0; i < count; i++) {
                        params[i] = activeStack.pop();
                        classes[i] = (Class<?>) activeStack.pop();
                    }
                    String methodName = sequence.dataPop();
                    String polymeraseName = sequence.dataPop();
                    Object polymerase = polymerases.get(polymeraseName);
                    if (polymerase == null) {
                        throw new ExpressException("根据[酶名称: " + polymeraseName + "]在酶列表中未能找到对应酶对象");
                    }
                    try {
                        Method method = polymerase.getClass().getMethod(methodName, classes);
                        Object value = method.invoke(polymerase, params);
                        activeStack.push(method.getReturnType());
                        if (value instanceof String) {
                            Object obj = execute(value.toString());
                            activeStack.push(obj);
                        } else {
                            activeStack.push(value);
                        }
                    } catch (NoSuchMethodException e) {
                        throw new ExpressException("方法[" + methodName + "]在酶[" + polymeraseName + "]中不存在");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new ExpressException("方法[" + methodName + "]在酶[" + polymeraseName + "]中执行报错", e);
                    }
                    count = countStack.pop() + 1;
                    break;
                default:
                    break;
            }
        }
        return activeStack.pop().toString();
    }
}
