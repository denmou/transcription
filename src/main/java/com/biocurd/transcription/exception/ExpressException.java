package com.biocurd.transcription.exception;

/**
 * 执行异常信息
 *
 * @author denmou
 * @date 2020/8/3 10:35
 */
public class ExpressException extends Exception {
    public ExpressException(String message) {
        super(message);
    }

    public ExpressException(String message, Throwable cause) {
        super(message, cause);
    }
}
