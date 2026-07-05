package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 几何实验室实体类
 * <p>
 * 对应数据库表 geometry_lab，存储学生保存的几何实验配置和数据，
 * 包括图形元素、变换操作和探索记录。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("geometry_lab")
public class GeometryLab implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实验ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（外键关联user表）
     */
    private Long userId;

    /**
     * 实验名称
     */
    private String name;

    /**
     * 实验类型：triangle-三角形, quadrilateral-四边形, circle-圆, coordinate-坐标系
     */
    private String labType;

    /**
     * 图形配置数据（JSON格式，存储几何图形的顶点坐标、边长、角度等参数）
     */
    private String configData;

    /**
     * 实验记录/笔记
     */
    private String notes;

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
