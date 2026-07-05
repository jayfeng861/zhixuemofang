package com.zhixue.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应封装类
 * <p>
 * 所有API接口的返回值统一使用此类进行封装，包含状态码、消息、数据和时间戳。
 * 格式为: {code, message, data, timestamp}
 * </p>
 *
 * @param <T> 响应数据的泛型类型
 * @author zhixue
 * @version 1.0.0
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码，200表示成功，其他表示各种错误
     */
    private Integer code;

    /**
     * 响应消息描述
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 私有构造方法
     */
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return Result实例
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    /**
     * 成功响应（不带数据）
     *
     * @return Result实例
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 成功响应（带自定义消息）
     *
     * @param message 自定义消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return Result实例
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 失败响应
     *
     * @param code    错误码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result实例
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败响应（默认500错误码）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result实例
     */
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
}
