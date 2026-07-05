package com.zhixue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.ai.AiMockEngine;
import com.zhixue.common.PageResult;
import com.zhixue.entity.Vocabulary;
import com.zhixue.mapper.VocabularyMapper;
import com.zhixue.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 词汇服务实现类
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class VocabularyServiceImpl extends ServiceImpl<VocabularyMapper, Vocabulary> implements VocabularyService {

    private final AiMockEngine aiMockEngine;

    /**
     * 词汇列表（支持分类、分页）
     */
    public PageResult<Vocabulary> listByPage(String category, Integer pageNum, Integer pageSize) {
        Page<Vocabulary> page = lambdaQuery()
                .eq(category != null && !category.isEmpty(), Vocabulary::getCategory, category)
                .orderByAsc(Vocabulary::getDifficultyLevel)
                .page(new Page<>(pageNum, pageSize));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    /**
     * 词汇测试
     */
    public Map<String, Object> testVocabulary(List<String> answers, List<Long> wordIds) {
        return aiMockEngine.testVocabulary(answers, wordIds);
    }
}
