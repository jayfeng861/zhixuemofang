package com.zhixue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 作文提交请求DTO
 * <p>
 * 接收前端提交作文的参数，包括标题和正文内容。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
public class EssaySubmitDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作文标题
     */
    @NotBlank(message = "作文标题不能为空")
    private String title;

    /**
     * 作文题目要求
     */
    private String requirement;

    /**
     * 作文正文内容
     */
    @NotBlank(message = "作文内容不能为空")
    private String content;
}
