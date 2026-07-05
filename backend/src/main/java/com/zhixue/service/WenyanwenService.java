package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.common.PageResult;
import com.zhixue.entity.WenyanwenScript;

public interface WenyanwenService extends IService<WenyanwenScript> {
    PageResult<WenyanwenScript> listByPage(Integer pageNum, Integer pageSize);
    WenyanwenScript getDetail(Long id);
}
