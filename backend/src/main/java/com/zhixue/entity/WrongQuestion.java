package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 错题实体类
 * <p>
 * 对应数据库表 wrong_question，记录学生的错题信息，
 * 包括题目内容、学生答案、正确答案和错误原因分析。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("wrong_question")
public class WrongQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错题ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（外键关联user表）
     */
    private Long userId;

    /**
     * 科目：chinese-语文, math-数学, english-英语
     */
    private String subject;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 题目类型：choice-选择题, fill-填空题, solve-解答题
     */
    private String questionType;

    /**
     * 学生作答内容
     */
    private String studentAnswer;

    /**
     * 正确答案
     */
    private String correctAnswer;

    /**
     * 知识点ID（外键关联knowledge_point表）
     */
    private Long knowledgePointId;

    /**
     * 知识点名称
     */
    private String knowledgePointName;

    /**
     * 错误原因（AI分析或学生自评）
     */
    private String errorReason;

    /**
     * 是否已掌握：0-未掌握，1-已掌握
     */
    private Integer mastered;

    /**
     * 错误次数
     */
    private Integer errorCount;

    /**
     * 逻辑删除标识
     */
    @TableLogic
    private Integer deleted;

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
