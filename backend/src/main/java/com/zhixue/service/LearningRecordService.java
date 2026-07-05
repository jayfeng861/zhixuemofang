package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.entity.LearningRecord;

import java.util.List;
import java.util.Map;

public interface LearningRecordService extends IService<LearningRecord> {
    Map<String, Object> getOverview(Long userId);
    Map<String, Object> getProgress(Long userId);
    List<Map<String, Object>> getRanking();
}
