package com.zhixue.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.common.BusinessException;
import com.zhixue.common.PageResult;
import com.zhixue.entity.Post;
import com.zhixue.mapper.PostMapper;
import com.zhixue.service.CommunityService;
import org.springframework.stereotype.Service;

/**
 * 社区服务实现类
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<PostMapper, Post> implements CommunityService {

    /**
     * 帖子列表（分页）
     */
    public PageResult<Post> listPosts(Integer pageNum, Integer pageSize) {
        Page<Post> page = lambdaQuery()
                .eq(Post::getParentId, 0)
                .orderByDesc(Post::getCreateTime)
                .page(new Page<>(pageNum, pageSize));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    /**
     * 发帖
     */
    public Post createPost(Long userId, String authorName, String authorAvatar, String title, String content, String postType, String tags, String images) {
        Post post = new Post();
        post.setUserId(userId);
        post.setAuthorName(authorName);
        post.setAuthorAvatar(authorAvatar);
        post.setTitle(title);
        post.setContent(content);
        post.setPostType(postType);
        post.setTags(tags);
        post.setImages(images);
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setParentId(0L);
        save(post);
        return post;
    }

    /**
     * 点赞
     */
    public void likePost(Long id) {
        Post post = getById(id);
        if (post == null) {
            throw new BusinessException("帖子不存在");
        }
        post.setLikeCount(post.getLikeCount() + 1);
        updateById(post);
    }

    /**
     * 评论
     */
    public Post commentPost(Long parentId, Long userId, String authorName, String authorAvatar, String content) {
        Post post = getById(parentId);
        if (post == null) {
            throw new BusinessException("帖子不存在");
        }
        // 增加评论数
        post.setCommentCount(post.getCommentCount() + 1);
        updateById(post);

        // 创建评论
        Post comment = new Post();
        comment.setUserId(userId);
        comment.setAuthorName(authorName);
        comment.setAuthorAvatar(authorAvatar);
        comment.setContent(content);
        comment.setParentId(parentId);
        comment.setPostType("comment");
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        save(comment);
        return comment;
    }
}
