package com.zhixue.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.ai.AiMockEngine;
import com.zhixue.common.PageResult;
import com.zhixue.dto.OralEvaluateDTO;
import com.zhixue.entity.OralPractice;
import com.zhixue.mapper.OralPracticeMapper;
import com.zhixue.service.OralService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 口语服务实现类
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class OralServiceImpl extends ServiceImpl<OralPracticeMapper, OralPractice> implements OralService {

    private final AiMockEngine aiMockEngine;

    /**
     * 口语场景列表（模拟数据）
     */
    public List<Map<String, Object>> listScenes() {
        List<Map<String, Object>> scenes = new ArrayList<>();
        scenes.add(Map.of("name", "自我介绍", "description", "练习用英语介绍自己", "difficulty", "easy"));
        scenes.add(Map.of("name", "课堂回答", "description", "模拟课堂用英语回答老师提问", "difficulty", "medium"));
        scenes.add(Map.of("name", "购物对话", "description", "模拟在商店购物的英语对话", "difficulty", "easy"));
        scenes.add(Map.of("name", "天气预报", "description", "用英语描述天气情况", "difficulty", "medium"));
        scenes.add(Map.of("name", "故事复述", "description", "用英语复述一个短故事", "difficulty", "hard"));
        return scenes;
    }

    /**
     * 口语评测
     */
    public OralPractice evaluate(Long userId, OralEvaluateDTO dto) {
        Map<String, Object> result = aiMockEngine.evaluateOral(dto.getSpeechText(), dto.getReferenceText(), dto.getSceneName());

        OralPractice practice = new OralPractice();
        practice.setUserId(userId);
        practice.setSceneName(dto.getSceneName());
        practice.setScenePrompt(dto.getScenePrompt());
        practice.setSpeechText(dto.getSpeechText());
        practice.setReferenceText(dto.getReferenceText());
        practice.setPronunciationScore((Integer) result.get("pronunciationScore"));
        practice.setIntonationScore((Integer) result.get("intonationScore"));
        practice.setFluencyScore((Integer) result.get("fluencyScore"));
        practice.setTotalScore((Integer) result.get("totalScore"));
        practice.setAiFeedback(JSONUtil.toJsonStr(result));
        practice.setDuration(dto.getDuration());
        save(practice);
        return practice;
    }

    /**
     * 口语练习历史
     */
    public PageResult<OralPractice> listHistory(Long userId, Integer pageNum, Integer pageSize) {
        Page<OralPractice> page = lambdaQuery()
                .eq(OralPractice::getUserId, userId)
                .orderByDesc(OralPractice::getCreateTime)
                .page(new Page<>(pageNum, pageSize));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
