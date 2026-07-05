package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.entity.StudentProfile;

public interface StudentProfileService extends IService<StudentProfile> {
    StudentProfile getByUserId(Long userId);
    void updateByUserId(Long userId, StudentProfile profile);
}
