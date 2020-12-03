package com.tyzz.mvc.exception;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/24
 * Time: 17:27
 */
public class SummerRuntimeException extends RuntimeException {
    public SummerRuntimeException() {
        super();
    }

    public SummerRuntimeException(String message) {
        super(message);
    }

    public SummerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
