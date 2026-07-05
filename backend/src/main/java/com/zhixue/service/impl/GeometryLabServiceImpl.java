package com.zhixue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.common.BusinessException;
import com.zhixue.entity.GeometryLab;
import com.zhixue.mapper.GeometryLabMapper;
import com.zhixue.service.GeometryLabService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 几何实验室服务实现类
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
public class GeometryLabServiceImpl extends ServiceImpl<GeometryLabMapper, GeometryLab> implements GeometryLabService {

    /**
     * 获取几何实验室预设配置
     */
    public List<Map<String, Object>> getConfigs() {
        List<Map<String, Object>> configs = new ArrayList<>();
        configs.add(Map.of("type", "triangle", "name", "三角形探索", "description", "探索三角形的性质，包括内角和、中线、角平分线等"));
        configs.add(Map.of("type", "quadrilateral", "name", "四边形探索", "description", "探索平行四边形、矩形、菱形、正方形的性质"));
        configs.add(Map.of("type", "circle", "name", "圆的探索", "description", "探索圆的性质，包括半径、直径、弧、弦等"));
        configs.add(Map.of("type", "coordinate", "name", "坐标系探索", "description", "在坐标系中进行图形变换和函数图像绘制"));
        configs.add(Map.of("type", "transform", "name", "图形变换", "description", "探索平移、旋转、对称等图形变换操作"));
        return configs;
    }

    /**
     * 保存几何实验
     */
    public GeometryLab saveLab(Long userId, String name, String labType, String configData, String notes) {
        GeometryLab lab = new GeometryLab();
        lab.setUserId(userId);
        lab.setName(name);
        lab.setLabType(labType);
        lab.setConfigData(configData);
        lab.setNotes(notes);
        save(lab);
        return lab;
    }
}
