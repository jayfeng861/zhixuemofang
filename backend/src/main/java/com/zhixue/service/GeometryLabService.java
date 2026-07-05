package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.entity.GeometryLab;

import java.util.List;
import java.util.Map;

public interface GeometryLabService extends IService<GeometryLab> {
    List<Map<String, Object>> getConfigs();
    GeometryLab saveLab(Long userId, String name, String labType, String configData, String notes);
}
