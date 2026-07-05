package com.zhixue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 错题请求DTO
 * <p>
 * 接收前端添加错题的参数，包括科目、题目内容、学生答案和正确答案。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
public class WrongQuestionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 科目：chinese-语文, math-数学, english-英语
     */
    @NotBlank(message = "科目不能为空")
    private String subject;

    /**
     * 题目内容
     */
    @NotBlank(message = "题目内容不能为空")
    private String content;

    /**
     * 题目类型：choice-选择题, fill-填空题, solve-解答题
     */
    @NotBlank(message = "题目类型不能为空")
    private String questionType;

    /**
     * 学生作答内容
     */
    @NotBlank(message = "学生答案不能为空")
    private String studentAnswer;

    /**
     * 正确答案
     */
    @NotBlank(message = "正确答案不能为空")
    private String correctAnswer;

    /**
     * 知识点ID
     */
    private Long knowledgePointId;

    /**
     * 知识点名称
     */
    private String knowledgePointName;

    /**
     * 错误原因（学生自评）
     */
    private String errorReason;
}
