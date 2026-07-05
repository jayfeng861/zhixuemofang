package com.zhixue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 口语评测请求DTO
 * <p>
 * 接收前端口语评测请求的参数，包括场景名称和学生口语文本。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
public class OralEvaluateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 场景名称
     */
    @NotBlank(message = "场景名称不能为空")
    private String sceneName;

    /**
     * 场景描述/提示文本
     */
    private String scenePrompt;

    /**
     * 学生口语录音文本（语音识别结果）
     */
    @NotBlank(message = "口语文本不能为空")
    private String speechText;

    /**
     * 参考答案/标准表达
     */
    private String referenceText;

    /**
     * 练习时长（秒）
     */
    private Integer duration;
}
