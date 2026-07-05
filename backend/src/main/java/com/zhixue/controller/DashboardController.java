package com.zhixue.controller;

import com.zhixue.common.Result;
import com.zhixue.service.LearningRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据看板控制器
 * <p>
 * 提供学习概览、学习进度和排行榜等数据统计接口。
 * 路径前缀：/api/v1/dashboard
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final LearningRecordService learningRecordService;

    /**
     * 学习概览数据
     * <p>
     * 获取当前用户的学习概览统计数据，包括今日/本周/累计学习时长等。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @return 学习概览数据Map
     */
    @GetMapping("/stats")
    public Result<Map<String, Object>> getOverview(@RequestAttribute("userId") Long userId) {
        return Result.success(learningRecordService.getOverview(userId));
    }

    /**
     * 学习进度
     * <p>
     * 获取当前用户各科目的学习进度和周目标完成情况。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @return 学习进度数据Map
     */
    @GetMapping("/weekly")
    public Result<Map<String, Object>> getProgress(@RequestAttribute("userId") Long userId) {
        return Result.success(learningRecordService.getProgress(userId));
    }

    /**
     * 知识点热力图
     * <p>
     * 获取用户知识点掌握情况的热力图数据。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @return 知识点热力图数据Map
     */
    @GetMapping("/knowledge-heatmap")
    public Result<Map<String, Object>> getKnowledgeHeatmap(@RequestAttribute("userId") Long userId) {
        return Result.success(learningRecordService.getOverview(userId));
    }

    /**
     * 排行榜
     * <p>
     * 获取积分排行榜数据。此接口不需要登录即可访问。
     * </p>
     *
     * @return 排行榜列表，包含排名、昵称、积分、头像
     */
    @GetMapping("/ranking")
    public Result<List<Map<String, Object>>> getRanking() {
        return Result.success(learningRecordService.getRanking());
    }
}
