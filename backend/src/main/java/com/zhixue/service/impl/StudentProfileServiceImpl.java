package com.zhixue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.common.BusinessException;
import com.zhixue.entity.StudentProfile;
import com.zhixue.mapper.StudentProfileMapper;
import com.zhixue.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 学生档案服务实现类
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl extends ServiceImpl<StudentProfileMapper, StudentProfile> implements StudentProfileService {

    /**
     * 获取学生档案
     *
     * @param userId 用户ID
     * @return 学生档案
     * @throws BusinessException 档案不存在时抛出
     */
    public StudentProfile getByUserId(Long userId) {
        StudentProfile profile = lambdaQuery()
                .eq(StudentProfile::getUserId, userId)
                .last("LIMIT 1")
                .one();
        if (profile == null) {
            throw new BusinessException("学生档案不存在");
        }
        return profile;
    }

    /**
     * 更新学生档案
     *
     * @param userId  用户ID
     * @param profile 更新内容
     */
    public void updateByUserId(Long userId, StudentProfile profile) {
        StudentProfile existing = getByUserId(userId);
        profile.setId(existing.getId());
        updateById(profile);
    }
}
