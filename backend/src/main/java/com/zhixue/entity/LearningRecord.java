package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学习记录实体类
 * <p>
 * 对应数据库表 learning_record，记录学生的每日学习活动，
 * 包括学习科目、学习时长、学习模块等信息。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("learning_record")
public class LearningRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（外键关联user表）
     */
    private Long userId;

    /**
     * 学习日期
     */
    private LocalDateTime studyDate;

    /**
     * 科目：chinese-语文, math-数学, english-英语
     */
    private String subject;

    /**
     * 学习模块：wenyanwen-文言文, essay-作文, vocabulary-词汇, oral-口语, wrong-错题等
     */
    private String module;

    /**
     * 学习时长（分钟）
     */
    private Integer duration;

    /**
     * 学习内容描述
     */
    private String content;

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
