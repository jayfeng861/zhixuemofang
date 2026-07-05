package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.common.PageResult;
import com.zhixue.entity.Vocabulary;

import java.util.List;
import java.util.Map;

public interface VocabularyService extends IService<Vocabulary> {
    PageResult<Vocabulary> listByPage(String category, Integer pageNum, Integer pageSize);
    Map<String, Object> testVocabulary(List<String> answers, List<Long> wordIds);
}
