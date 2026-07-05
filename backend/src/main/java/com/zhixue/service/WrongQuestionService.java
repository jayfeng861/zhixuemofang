package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.common.PageResult;
import com.zhixue.dto.WrongQuestionDTO;
import com.zhixue.entity.WrongQuestion;

public interface WrongQuestionService extends IService<WrongQuestion> {
    PageResult<WrongQuestion> listByPage(Long userId, Integer pageNum, Integer pageSize);
    WrongQuestion addWrongQuestion(Long userId, WrongQuestionDTO dto);
    void deleteWrongQuestion(Long userId, Long id);
}
