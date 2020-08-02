package com.biocurd.transcription.function;

/**
 * 双参数无返回的函数接口
 *
 * @author denmou
 * @date 2020/8/3 00:07
 */
@FunctionalInterface
public interface IExecute<N, M> {
    /**
     * 执行方法
     *
     * @param n 参数1
     * @param m 参数2
     */
    void apply(N n, M m);

    /**
     * 后置执行
     *
     * @param after 后置方法
     * @return 组合函数，先自行自身，再执行后置
     */
    default IExecute<N, M> andThen(IExecute<N, M> after) {
        return (n, m) -> {
            this.apply(n, m);
            after.apply(n, m);
        };
    }

    /**
     * 前置执行
     *
     * @param before 前置方法
     * @return 组合函数，先自行前置，再执行自身
     */
    default IExecute<N, M> compose(IExecute<N, M> before) {
        return (n, m) -> {
            before.apply(n, m);
            this.apply(n, m);
        };
    }
}
