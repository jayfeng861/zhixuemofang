package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.common.PageResult;
import com.zhixue.dto.EssaySubmitDTO;
import com.zhixue.entity.EssaySubmission;

public interface EssayService extends IService<EssaySubmission> {
    EssaySubmission submitEssay(Long userId, EssaySubmitDTO dto);
    PageResult<EssaySubmission> listByPage(Long userId, Integer pageNum, Integer pageSize);
    EssaySubmission getDetail(Long userId, Long id);
}
