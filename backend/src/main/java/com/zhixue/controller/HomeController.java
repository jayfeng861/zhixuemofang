package com.zhixue.controller;

import com.zhixue.common.Result;
import com.zhixue.service.WenyanwenService;
import com.zhixue.service.VocabularyService;
import com.zhixue.service.WrongQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final WenyanwenService wenyanwenService;
    private final VocabularyService vocabularyService;
    private final WrongQuestionService wrongQuestionService;

    @GetMapping("/stats")
    public Result<Map<String, Object>> getHomeStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("todayMinutes", 100000);
        stats.put("totalMinutes", 5000000);
        stats.put("essayCount", 100000);
        stats.put("wrongQuestionCount", 1000000);
        return Result.success(stats);
    }

    @GetMapping("/announcements")
    public Result<List<Map<String, Object>>> getAnnouncements() {
        List<Map<String, Object>> announcements = new ArrayList<>();
        
        Map<String, Object> ann1 = new HashMap<>();
        ann1.put("id", 1);
        ann1.put("title", "智学魔方V1.0正式上线");
        ann1.put("content", "全新AI学习辅导功能正式上线！");
        ann1.put("createTime", "2024-01-15");
        announcements.add(ann1);

        Map<String, Object> ann2 = new HashMap<>();
        ann2.put("id", 2);
        ann2.put("title", "期末冲刺活动开启");
        ann2.put("content", "每日打卡赢取学习奖励！");
        ann2.put("createTime", "2024-01-10");
        announcements.add(ann2);

        Map<String, Object> ann3 = new HashMap<>();
        ann3.put("id", 3);
        ann3.put("title", "新增文言文剧场功能");
        ann3.put("content", "沉浸式学习文言文，感受传统文化魅力！");
        ann3.put("createTime", "2024-01-05");
        announcements.add(ann3);

        return Result.success(announcements);
    }
}