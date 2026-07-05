package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 每日打卡实体类
 * <p>
 * 对应数据库表 daily_checkin，记录学生的每日学习打卡信息，
 * 用于统计学习连续天数和激励学习。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("daily_checkin")
public class DailyCheckin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 打卡ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（外键关联user表）
     */
    private Long userId;

    /**
     * 打卡日期
     */
    private LocalDateTime checkinDate;

    /**
     * 打卡类型：daily-每日打卡, milestone-里程碑打卡
     */
    private String checkinType;

    /**
     * 打卡备注/学习内容
     */
    private String remark;

    /**
     * 获得积分
     */
    private Integer points;

    /**
     * 连续打卡天数
     */
    private Integer continuousDays;

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
