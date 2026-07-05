package com.zhixue.controller;

import com.zhixue.common.PageResult;
import com.zhixue.common.Result;
import com.zhixue.entity.Post;
import com.zhixue.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区模块控制器
 * <p>
 * 处理学习社区相关的请求，包括帖子列表、发帖、点赞和评论功能。
 * 路径前缀：/api/v1/community
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    /**
     * 帖子列表
     * <p>
     * 分页获取社区帖子列表，按创建时间倒序排列。
     * 此接口不需要登录即可访问。
     * </p>
     *
     * @param pageNum  当前页码，默认1
     * @param pageSize 每页大小，默认10
     * @return 帖子分页列表
     */
    @GetMapping("/posts")
    public Result<PageResult<Post>> listPosts(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(communityService.listPosts(pageNum, pageSize));
    }

    /**
     * 发帖
     * <p>
     * 创建新的社区帖子。支持提问、分享和讨论类型。
     * </p>
     *
     * @param userId     从JWT Token中解析出的用户ID
     * @param requestBody 发帖数据，包含title、content、postType、tags、images
     * @return 创建的帖子信息
     */
    @PostMapping("/posts")
    public Result<Post> createPost(@RequestAttribute("userId") Long userId,
                                    @RequestBody Map<String, Object> requestBody) {
        String title = (String) requestBody.get("title");
        String content = (String) requestBody.get("content");
        String postType = (String) requestBody.getOrDefault("postType", "discussion");
        String tags = (String) requestBody.get("tags");
        String images = (String) requestBody.get("images");
        return Result.success("发帖成功", communityService.createPost(userId, "用户" + userId, "", title, content, postType, tags, images));
    }

    /**
     * 点赞
     * <p>
     * 对指定帖子进行点赞操作。
     * </p>
     *
     * @param postId 帖子ID
     * @return 点赞是否成功
     */
    @PostMapping("/posts/{id}/like")
    public Result<Void> likePost(@PathVariable("id") Long postId) {
        communityService.likePost(postId);
        return Result.success("点赞成功", null);
    }

    /**
     * 评论
     * <p>
     * 对指定帖子发表评论。
     * </p>
     *
     * @param userId     从JWT Token中解析出的用户ID
     * @param postId     帖子ID
     * @param requestBody 评论数据，包含content
     * @return 创建的评论信息
     */
    @PostMapping("/posts/{id}/comments")
    public Result<Post> commentPost(@RequestAttribute("userId") Long userId,
                                    @PathVariable("id") Long postId,
                                    @RequestBody Map<String, String> requestBody) {
        String content = requestBody.get("content");
        return Result.success("评论成功", communityService.commentPost(postId, userId, "用户" + userId, "", content));
    }
}
