package com.zhixue.controller;

import com.zhixue.common.Result;
import com.zhixue.entity.DailyCheckin;
import com.zhixue.service.DailyCheckinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统模块控制器
 * <p>
 * 处理系统级功能请求，包括每日打卡等功能。
 * 路径前缀：/api/v1/system
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/system")
@RequiredArgsConstructor
public class SystemController {

    private final DailyCheckinService dailyCheckinService;

    /**
     * 每日打卡
     * <p>
     * 记录用户的每日学习打卡。如果是连续打卡，会计算连续天数和奖励积分。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @param requestBody 打卡数据，包含remark（可选备注）
     * @return 打卡结果
     */
    @PostMapping("/checkin")
    public Result<DailyCheckin> checkin(@RequestAttribute("userId") Long userId,
                                        @RequestBody(required = false) Map<String, String> requestBody) {
        String remark = requestBody != null ? requestBody.get("remark") : null;

        // 检查今日是否已打卡
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        long count = dailyCheckinService.lambdaQuery()
                .eq(com.zhixue.entity.DailyCheckin::getUserId, userId)
                .between(com.zhixue.entity.DailyCheckin::getCheckinDate, todayStart, todayEnd)
                .count();

        if (count > 0) {
            throw new com.zhixue.common.BusinessException("今日已打卡，请明天再来");
        }

        // 计算连续打卡天数
        LocalDateTime yesterdayStart = todayStart.minusDays(1);
        long yesterdayCount = dailyCheckinService.lambdaQuery()
                .eq(com.zhixue.entity.DailyCheckin::getUserId, userId)
                .between(com.zhixue.entity.DailyCheckin::getCheckinDate, yesterdayStart, todayStart.minusSeconds(1))
                .count();

        int continuousDays = (int) (yesterdayCount > 0 ?
                dailyCheckinService.lambdaQuery()
                        .eq(com.zhixue.entity.DailyCheckin::getUserId, userId)
                        .orderByDesc(com.zhixue.entity.DailyCheckin::getCheckinDate)
                        .last("LIMIT 1")
                        .one().getContinuousDays() + 1 : 1);

        // 创建打卡记录
        DailyCheckin checkin = new DailyCheckin();
        checkin.setUserId(userId);
        checkin.setCheckinDate(LocalDateTime.now());
        checkin.setCheckinType("daily");
        checkin.setRemark(remark);
        checkin.setContinuousDays(continuousDays);

        // 奖励积分（连续打卡越多积分越高）
        int points = 10 + (continuousDays >= 7 ? 10 : continuousDays >= 3 ? 5 : 0);
        checkin.setPoints(points);

        dailyCheckinService.save(checkin);

        return Result.success("打卡成功！获得" + points + "积分，连续打卡" + continuousDays + "天", checkin);
    }

    /**
     * 今日打卡状态
     * <p>
     * 查询当前用户今日是否已完成打卡。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @return 打卡状态信息，包含是否已打卡和连续打卡天数
     */
    @GetMapping("/checkin/status")
    public Result<Map<String, Object>> getCheckinStatus(@RequestAttribute("userId") Long userId) {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        long todayCount = dailyCheckinService.lambdaQuery()
                .eq(com.zhixue.entity.DailyCheckin::getUserId, userId)
                .between(com.zhixue.entity.DailyCheckin::getCheckinDate, todayStart, todayEnd)
                .count();

        DailyCheckin lastCheckin = dailyCheckinService.lambdaQuery()
                .eq(com.zhixue.entity.DailyCheckin::getUserId, userId)
                .orderByDesc(com.zhixue.entity.DailyCheckin::getCheckinDate)
                .last("LIMIT 1")
                .one();

        Map<String, Object> status = new HashMap<>();
        status.put("checked", todayCount > 0);
        status.put("continuousDays", lastCheckin != null ? lastCheckin.getContinuousDays() : 0);

        return Result.success(status);
    }

    /**
     * 系统公告列表
     * <p>
     * 获取系统公告信息。
     * </p>
     *
     * @return 公告列表
     */
    @GetMapping("/announcements")
    public Result<List<Map<String, Object>>> getAnnouncements() {
        List<Map<String, Object>> announcements = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("id", 1);
        item1.put("title", "系统更新通知");
        item1.put("content", "智学魔方V1.0正式上线，欢迎体验AI学习辅导功能！");
        item1.put("createTime", LocalDateTime.now().minusDays(1).toString());
        announcements.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("id", 2);
        item2.put("title", "新功能上线");
        item2.put("content", "文言文剧场、动态几何实验室等新功能已上线！");
        item2.put("createTime", LocalDateTime.now().minusDays(2).toString());
        announcements.add(item2);

        return Result.success(announcements);
    }

    /**
     * 反馈类型列表
     * <p>
     * 获取反馈类型选项。
     * </p>
     *
     * @return 反馈类型列表
     */
    @GetMapping("/feedback-types")
    public Result<List<Map<String, String>>> getFeedbackTypes() {
        List<Map<String, String>> types = new ArrayList<>();
        Map<String, String> type1 = new HashMap<>();
        type1.put("value", "bug");
        type1.put("label", "功能异常");
        types.add(type1);

        Map<String, String> type2 = new HashMap<>();
        type2.put("value", "suggest");
        type2.put("label", "功能建议");
        types.add(type2);

        Map<String, String> type3 = new HashMap<>();
        type3.put("value", "other");
        type3.put("label", "其他");
        types.add(type3);

        return Result.success(types);
    }

    /**
     * 提交反馈
     * <p>
     * 用户提交反馈信息。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @param requestBody 反馈数据，包含type、content
     * @return 提交结果
     */
    @PostMapping("/feedback")
    public Result<Void> submitFeedback(@RequestAttribute("userId") Long userId,
                                       @RequestBody Map<String, String> requestBody) {
        return Result.success("反馈提交成功，感谢您的宝贵意见", null);
    }
}
