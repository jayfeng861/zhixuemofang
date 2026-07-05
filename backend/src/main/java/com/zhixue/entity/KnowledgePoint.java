package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 知识点实体类
 * <p>
 * 对应数据库表 knowledge_point，存储各科目的知识点体系，
 * 支持知识点之间的层级关联和错题溯源分析。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("knowledge_point")
public class KnowledgePoint implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 知识点ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 科目：chinese-语文, math-数学, english-英语
     */
    private String subject;

    /**
     * 知识点名称
     */
    private String name;

    /**
     * 知识点描述
     */
    private String description;

    /**
     * 父知识点ID（0表示顶级知识点）
     */
    private Long parentId;

    /**
     * 知识点层级深度
     */
    private Integer level;

    /**
     * 排序序号
     */
    private Integer sortOrder;

    /**
     * 难度等级：1-简单, 2-中等, 3-困难
     */
    private Integer difficultyLevel;

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
