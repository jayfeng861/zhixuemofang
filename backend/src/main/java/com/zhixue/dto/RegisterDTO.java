package com.zhixue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 注册请求DTO
 * <p>
 * 接收前端注册请求的参数，包括用户名、密码、昵称和年级。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度为3-20个字符")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度为6-20个字符")
    private String password;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 年级：初一、初二、初三
     */
    @NotBlank(message = "年级不能为空")
    private String grade;
}
