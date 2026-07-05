package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 词汇实体类
 * <p>
 * 对应数据库表 vocabulary，存储英语词汇信息，
 * 包括单词、释义、音标、例句等，支持按分类管理。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("vocabulary")
public class Vocabulary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 词汇ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 单词
     */
    private String word;

    /**
     * 词性：noun-名词, verb-动词, adj-形容词, adv-副词, prep-介词等
     */
    private String partOfSpeech;

    /**
     * 中文释义
     */
    private String meaning;

    /**
     * 英式音标
     */
    private String phoneticUk;

    /**
     * 美式音标
     */
    private String phoneticUs;

    /**
     * 例句
     */
    private String exampleSentence;

    /**
     * 例句翻译
     */
    private String exampleTranslation;

    /**
     * 分类：core-核心词汇, extend-拓展词汇, exam-考试高频词
     */
    private String category;

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
