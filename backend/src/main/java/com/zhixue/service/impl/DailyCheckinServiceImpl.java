package com.zhixue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.entity.DailyCheckin;
import com.zhixue.mapper.DailyCheckinMapper;
import com.zhixue.service.DailyCheckinService;
import org.springframework.stereotype.Service;

@Service
public class DailyCheckinServiceImpl extends ServiceImpl<DailyCheckinMapper, DailyCheckin> implements DailyCheckinService {
}