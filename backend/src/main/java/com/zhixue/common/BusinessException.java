package com.zhixue.common;

import lombok.Getter;

/**
 * 业务异常类
 * <p>
 * 用于在Service层抛出业务逻辑相关的异常。
 * 异常包含错误码和错误消息，由全局异常处理器统一捕获处理。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 构造业务异常（默认错误码400）
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 400;
    }

    /**
     * 构造业务异常（自定义错误码）
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
