package com.zhixue.controller;

import com.zhixue.common.Result;
import com.zhixue.entity.StudentProfile;
import com.zhixue.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * <p>
 * 处理学生档案的查询和更新请求。
 * 路径前缀：/api/v1/user
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final StudentProfileService studentProfileService;

    /**
     * 获取学生档案
     * <p>
     * 根据当前登录用户的ID查询其学生档案信息。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @return 学生档案信息
     */
    @GetMapping("/profile")
    public Result<StudentProfile> getProfile(@RequestAttribute("userId") Long userId) {
        return Result.success(studentProfileService.getByUserId(userId));
    }

    /**
     * 更新学生档案
     * <p>
     * 更新当前登录用户的学生档案信息，如各科成绩、学习偏好和学习目标等。
     * </p>
     *
     * @param userId  从JWT Token中解析出的用户ID
     * @param profile 更新后的学生档案数据
     * @return 更新是否成功
     */
    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestAttribute("userId") Long userId, @RequestBody StudentProfile profile) {
        studentProfileService.updateByUserId(userId, profile);
        return Result.success("更新成功", null);
    }
}
