package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.common.PageResult;
import com.zhixue.dto.OralEvaluateDTO;
import com.zhixue.entity.OralPractice;

import java.util.List;
import java.util.Map;

public interface OralService extends IService<OralPractice> {
    List<Map<String, Object>> listScenes();
    OralPractice evaluate(Long userId, OralEvaluateDTO dto);
    PageResult<OralPractice> listHistory(Long userId, Integer pageNum, Integer pageSize);
}
