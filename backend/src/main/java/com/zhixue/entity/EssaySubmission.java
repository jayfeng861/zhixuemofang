package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 作文提交实体类
 * <p>
 * 对应数据库表 essay_submission，记录学生提交的作文信息，
 * 以及AI批改后的评分和详细评价结果。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("essay_submission")
public class EssaySubmission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作文ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（外键关联user表）
     */
    private Long userId;

    /**
     * 作文标题
     */
    private String title;

    /**
     * 作文题目要求
     */
    private String requirement;

    /**
     * 作文正文内容
     */
    private String content;

    /**
     * 字数统计
     */
    private Integer wordCount;

    /**
     * AI批改总分（满分100分）
     */
    private Integer totalScore;

    /**
     * 立意评分（满分25分）
     */
    private Integer themeScore;

    /**
     * 结构评分（满分25分）
     */
    private Integer structureScore;

    /**
     * 语言评分（满分25分）
     */
    private Integer languageScore;

    /**
     * 逻辑评分（满分25分）
     */
    private Integer logicScore;

    /**
     * AI批改评语（JSON格式，包含各维度的详细建议）
     */
    private String aiReview;

    /**
     * 批改状态：0-待批改，1-已批改
     */
    private Integer reviewStatus;

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
