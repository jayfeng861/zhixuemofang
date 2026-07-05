package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生档案实体类
 * <p>
 * 对应数据库表 student_profile，存储学生的学习偏好、
 * 各科目成绩和学习目标等详细档案信息。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("student_profile")
public class StudentProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 档案ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（外键关联user表）
     */
    private Long userId;

    /**
     * 语文成绩
     */
    private Integer chineseScore;

    /**
     * 数学成绩
     */
    private Integer mathScore;

    /**
     * 英语成绩
     */
    private Integer englishScore;

    /**
     * 学习偏好（JSON格式，记录学生偏好的学习方式）
     */
    private String learningPreference;

    /**
     * 学习目标
     */
    private String learningGoal;

    /**
     * 学习天数（累计打卡天数）
     */
    private Integer studyDays;

    /**
     * 总积分
     */
    private Integer totalPoints;

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
