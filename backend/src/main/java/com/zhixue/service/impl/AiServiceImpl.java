package com.zhixue.service.impl;

import com.zhixue.ai.AiMockEngine;
import com.zhixue.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * AI模拟服务实现类
 * <p>
 * 封装AiMockEngine，提供统一的AI服务调用接口。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiMockEngine aiMockEngine;

    /**
     * 作文批改
     */
    public Map<String, Object> reviewEssay(String content, String title) {
        return aiMockEngine.reviewEssay(content, title);
    }

    /**
     * 口语评测
     */
    public Map<String, Object> evaluateOral(String speechText, String referenceText, String sceneName) {
        return aiMockEngine.evaluateOral(speechText, referenceText, sceneName);
    }

    /**
     * 错题分析
     */
    public Map<String, Object> analyzeWrongQuestions(String subject, Long userId) {
        return aiMockEngine.analyzeWrongQuestions(subject, userId);
    }

    /**
     * 词汇测试
     */
    public Map<String, Object> testVocabulary(List<String> answers, List<Long> wordIds) {
        return aiMockEngine.testVocabulary(answers, wordIds);
    }
}
