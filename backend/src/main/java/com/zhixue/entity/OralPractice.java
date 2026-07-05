package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 口语练习实体类
 * <p>
 * 对应数据库表 oral_practice，记录学生的英语口语练习记录，
 * 包括练习场景、录音内容、AI评分和评价结果。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("oral_practice")
public class OralPractice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 练习ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（外键关联user表）
     */
    private Long userId;

    /**
     * 场景名称（如：自我介绍、购物对话、课堂回答等）
     */
    private String sceneName;

    /**
     * 场景描述/提示文本
     */
    private String scenePrompt;

    /**
     * 学生口语录音文本（语音识别结果）
     */
    private String speechText;

    /**
     * 参考答案/标准表达
     */
    private String referenceText;

    /**
     * 发音评分（满分40分）
     */
    private Integer pronunciationScore;

    /**
     * 语调评分（满分30分）
     */
    private Integer intonationScore;

    /**
     * 语速评分（满分30分）
     */
    private Integer fluencyScore;

    /**
     * 总评分（满分100分）
     */
    private Integer totalScore;

    /**
     * AI评价（JSON格式，包含发音问题、改进建议等）
     */
    private String aiFeedback;

    /**
     * 练习时长（秒）
     */
    private Integer duration;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
