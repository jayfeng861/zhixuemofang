package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统设置实体类
 * <p>
 * 对应数据库表 sys_setting，存储系统配置信息。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("sys_setting")
public class SysSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设置ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设置键（唯一）
     */
    private String settingKey;

    /**
     * 设置值
     */
    private String settingValue;

    /**
     * 设置描述
     */
    private String settingDesc;

    /**
     * 设置分组
     */
    private String settingGroup;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
