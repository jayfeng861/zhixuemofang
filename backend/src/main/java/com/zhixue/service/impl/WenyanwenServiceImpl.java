package com.zhixue.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.common.BusinessException;
import com.zhixue.common.PageResult;
import com.zhixue.entity.WenyanwenScript;
import com.zhixue.mapper.WenyanwenScriptMapper;
import com.zhixue.service.WenyanwenService;
import org.springframework.stereotype.Service;

/**
 * 文言文服务实现类
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
public class WenyanwenServiceImpl extends ServiceImpl<WenyanwenScriptMapper, WenyanwenScript> implements WenyanwenService {

    /**
     * 文言文剧本列表（分页）
     */
    public PageResult<WenyanwenScript> listByPage(Integer pageNum, Integer pageSize) {
        Page<WenyanwenScript> page = lambdaQuery()
                .orderByAsc(WenyanwenScript::getDifficultyLevel)
                .page(new Page<>(pageNum, pageSize));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    /**
     * 文言文剧本详情
     */
    public WenyanwenScript getDetail(Long id) {
        WenyanwenScript script = getById(id);
        if (script == null) {
            throw new BusinessException("剧本不存在");
        }
        return script;
    }
}
