package com.zhixue.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.common.BusinessException;
import com.zhixue.common.PageResult;
import com.zhixue.dto.WrongQuestionDTO;
import com.zhixue.entity.WrongQuestion;
import com.zhixue.mapper.WrongQuestionMapper;
import com.zhixue.service.WrongQuestionService;
import org.springframework.stereotype.Service;

/**
 * 错题服务实现类
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
public class WrongQuestionServiceImpl extends ServiceImpl<WrongQuestionMapper, WrongQuestion> implements WrongQuestionService {

    /**
     * 分页查询错题列表
     */
    public PageResult<WrongQuestion> listByPage(Long userId, Integer pageNum, Integer pageSize) {
        Page<WrongQuestion> page = lambdaQuery()
                .eq(WrongQuestion::getUserId, userId)
                .orderByDesc(WrongQuestion::getCreateTime)
                .page(new Page<>(pageNum, pageSize));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    /**
     * 添加错题
     */
    public WrongQuestion addWrongQuestion(Long userId, WrongQuestionDTO dto) {
        WrongQuestion wq = new WrongQuestion();
        wq.setUserId(userId);
        wq.setSubject(dto.getSubject());
        wq.setContent(dto.getContent());
        wq.setQuestionType(dto.getQuestionType());
        wq.setStudentAnswer(dto.getStudentAnswer());
        wq.setCorrectAnswer(dto.getCorrectAnswer());
        wq.setKnowledgePointId(dto.getKnowledgePointId());
        wq.setKnowledgePointName(dto.getKnowledgePointName());
        wq.setErrorReason(dto.getErrorReason());
        wq.setMastered(0);
        wq.setErrorCount(1);
        save(wq);
        return wq;
    }

    /**
     * 删除错题
     */
    public void deleteWrongQuestion(Long userId, Long id) {
        WrongQuestion wq = getById(id);
        if (wq == null || !wq.getUserId().equals(userId)) {
            throw new BusinessException("错题不存在");
        }
        removeById(id);
    }
}
