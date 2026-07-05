package com.zhixue.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.ai.AiMockEngine;
import com.zhixue.common.PageResult;
import com.zhixue.dto.EssaySubmitDTO;
import com.zhixue.entity.EssaySubmission;
import com.zhixue.mapper.EssaySubmissionMapper;
import com.zhixue.service.EssayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 作文服务实现类
 * <p>
 * 实现作文提交、AI批改和作文列表查询功能。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class EssayServiceImpl extends ServiceImpl<EssaySubmissionMapper, EssaySubmission> implements EssayService {

    private final AiMockEngine aiMockEngine;

    /**
     * 提交作文并进行AI批改
     */
    public EssaySubmission submitEssay(Long userId, EssaySubmitDTO dto) {
        EssaySubmission essay = new EssaySubmission();
        essay.setUserId(userId);
        essay.setTitle(dto.getTitle());
        essay.setRequirement(dto.getRequirement());
        essay.setContent(dto.getContent());
        essay.setWordCount(dto.getContent().length());

        // 调用AI模拟引擎批改
        Map<String, Object> reviewResult = aiMockEngine.reviewEssay(dto.getContent(), dto.getTitle());
        essay.setTotalScore((Integer) reviewResult.get("totalScore"));
        essay.setThemeScore((Integer) reviewResult.get("themeScore"));
        essay.setStructureScore((Integer) reviewResult.get("structureScore"));
        essay.setLanguageScore((Integer) reviewResult.get("languageScore"));
        essay.setLogicScore((Integer) reviewResult.get("logicScore"));
        essay.setAiReview(JSONUtil.toJsonStr(reviewResult));
        essay.setReviewStatus(1);

        save(essay);
        return essay;
    }

    /**
     * 我的作文列表（分页）
     */
    public PageResult<EssaySubmission> listByPage(Long userId, Integer pageNum, Integer pageSize) {
        Page<EssaySubmission> page = lambdaQuery()
                .eq(EssaySubmission::getUserId, userId)
                .orderByDesc(EssaySubmission::getCreateTime)
                .page(new Page<>(pageNum, pageSize));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    /**
     * 作文详情
     */
    public EssaySubmission getDetail(Long userId, Long id) {
        EssaySubmission essay = getById(id);
        if (essay == null || !essay.getUserId().equals(userId)) {
            throw new com.zhixue.common.BusinessException("作文不存在");
        }
        return essay;
    }
}
