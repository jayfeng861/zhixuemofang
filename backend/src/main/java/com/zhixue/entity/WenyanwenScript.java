package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文言文剧本实体类
 * <p>
 * 对应数据库表 wenyanwen_script，存储文言文互动剧本，
 * 包含原文、译文、场景描述和对话内容，支持沉浸式学习。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("wenyanwen_script")
public class WenyanwenScript implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 剧本ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 剧本标题（如：《桃花源记》、《出师表》等）
     */
    private String title;

    /**
     * 作者/出处
     */
    private String author;

    /**
     * 朝代
     */
    private String dynasty;

    /**
     * 剧本简介
     */
    private String description;

    /**
     * 原文内容
     */
    private String originalText;

    /**
     * 现代文翻译
     */
    private String translation;

    /**
     * 场景列表（JSON格式，包含各场景的背景描述和对话）
     */
    private String scenes;

    /**
     * 重点词汇注释（JSON格式）
     */
    private String annotations;

    /**
     * 难度等级：1-简单, 2-中等, 3-困难
     */
    private Integer difficultyLevel;

    /**
     * 封面图片URL
     */
    private String coverImage;

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
