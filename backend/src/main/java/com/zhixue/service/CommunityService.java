package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.common.PageResult;
import com.zhixue.entity.Post;

public interface CommunityService extends IService<Post> {
    PageResult<Post> listPosts(Integer pageNum, Integer pageSize);
    Post createPost(Long userId, String authorName, String authorAvatar, String title, String content, String postType, String tags, String images);
    void likePost(Long id);
    Post commentPost(Long parentId, Long userId, String authorName, String authorAvatar, String content);
}
