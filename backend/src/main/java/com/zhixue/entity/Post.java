package com.zhixue.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 社区帖子实体类
 * <p>
 * 对应数据库表 post，存储学习社区中的帖子和评论信息，
 * 支持点赞、评论等互动功能。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Data
@TableName("post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子ID（主键，自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（发帖人，外键关联user表）
     */
    private Long userId;

    /**
     * 发帖人昵称（冗余字段，方便展示）
     */
    private String authorName;

    /**
     * 发帖人头像
     */
    private String authorAvatar;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子类型：question-提问, share-分享, discussion-讨论
     */
    private String postType;

    /**
     * 标签（JSON格式数组）
     */
    private String tags;

    /**
     * 图片列表（JSON格式数组，存储图片URL）
     */
    private String images;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 父帖子ID（用于评论功能，0表示顶级帖子）
     */
    private Long parentId;

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
