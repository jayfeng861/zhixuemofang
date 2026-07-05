package com.zhixue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.entity.LearningRecord;
import com.zhixue.mapper.LearningRecordMapper;
import com.zhixue.service.LearningRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学习记录服务实现类
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
public class LearningRecordServiceImpl extends ServiceImpl<LearningRecordMapper, LearningRecord> implements LearningRecordService {

    /**
     * 获取学习概览数据
     */
    public Map<String, Object> getOverview(Long userId) {
        Map<String, Object> overview = new HashMap<>();
        // 模拟数据
        overview.put("todayMinutes", 45);
        overview.put("totalMinutes", 1280);
        overview.put("weekMinutes", 210);
        overview.put("essayCount", 5);
        overview.put("wrongQuestionCount", 12);
        overview.put("oralPracticeCount", 8);
        overview.put("vocabularyMastered", 156);
        return overview;
    }

    /**
     * 获取学习进度
     */
    public Map<String, Object> getProgress(Long userId) {
        Map<String, Object> progress = new HashMap<>();
        progress.put("chineseProgress", 68);
        progress.put("mathProgress", 72);
        progress.put("englishProgress", 65);
        progress.put("weeklyGoalMinutes", 300);
        progress.put("weeklyActualMinutes", 210);
        progress.put("weeklyCompletionRate", 70);
        return progress;
    }

    /**
     * 获取排行榜
     */
    public List<Map<String, Object>> getRanking() {
        List<Map<String, Object>> ranking = new java.util.ArrayList<>();
        ranking.add(Map.of("rank", 1, "nickname", "学霸小明", "points", 2580, "avatar", "https://api.dicebear.com/7.x/avataaars/svg?seed=xiaoming"));
        ranking.add(Map.of("rank", 2, "nickname", "努力小红", "points", 2350, "avatar", "https://api.dicebear.com/7.x/avataaars/svg?seed=xiaohong"));
        ranking.add(Map.of("rank", 3, "nickname", "加油小李", "points", 2100, "avatar", "https://api.dicebear.com/7.x/avataaars/svg?seed=xiaoli"));
        ranking.add(Map.of("rank", 4, "nickname", "勤奋小张", "points", 1890, "avatar", "https://api.dicebear.com/7.x/avataaars/svg?seed=xiaozhang"));
        ranking.add(Map.of("rank", 5, "nickname", "认真小王", "points", 1750, "avatar", "https://api.dicebear.com/7.x/avataaars/svg?seed=xiaowang"));
        return ranking;
    }

    /**
     * 记录学习
     */
    public void recordLearning(Long userId, String subject, String module, Integer duration, String content) {
        LearningRecord record = new LearningRecord();
        record.setUserId(userId);
        record.setStudyDate(LocalDateTime.now());
        record.setSubject(subject);
        record.setModule(module);
        record.setDuration(duration);
        record.setContent(content);
        save(record);
    }
}
