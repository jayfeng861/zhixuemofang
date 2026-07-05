package com.zhixue.service;

import java.util.List;
import java.util.Map;

/**
 * AI模拟服务接口
 * <p>
 * 提供AI模拟批改、口语评测、错题分析等服务方法。
 * 所有AI调用均为模拟实现，返回格式与真实AI一致。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
public interface AiService {

    /**
     * 作文批改
     *
     * @param content 作文正文
     * @param title   作文标题
     * @return 批改结果
     */
    Map<String, Object> reviewEssay(String content, String title);

    /**
     * 口语评测
     *
     * @param speechText    口语文本
     * @param referenceText 参考文本
     * @param sceneName     场景名称
     * @return 评测结果
     */
    Map<String, Object> evaluateOral(String speechText, String referenceText, String sceneName);

    /**
     * 错题分析
     *
     * @param subject 科目
     * @param userId  用户ID
     * @return 分析结果
     */
    Map<String, Object> analyzeWrongQuestions(String subject, Long userId);

    /**
     * 词汇测试
     *
     * @param answers 答案列表
     * @param wordIds 词汇ID列表
     * @return 测试结果
     */
    Map<String, Object> testVocabulary(List<String> answers, List<Long> wordIds);
}
