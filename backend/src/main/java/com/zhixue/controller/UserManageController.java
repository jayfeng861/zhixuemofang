package com.zhixue.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixue.common.BusinessException;
import com.zhixue.common.PageResult;
import com.zhixue.common.Result;
import com.zhixue.dto.PageQueryDTO;
import com.zhixue.entity.User;
import com.zhixue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户管理控制器
 * <p>
 * 处理后台用户管理相关请求。
 * 路径前缀：/api/v1/admin/users
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserManageController {

    private final UserService userService;

    /**
     * 获取用户列表（分页，支持按角色筛选）
     *
     * @param pageQueryDTO 分页参数
     * @param role         角色筛选（可选）
     * @param keyword      关键词搜索（用户名/昵称）
     * @return 分页后的用户列表
     */
    @GetMapping("/list")
    public Result<PageResult<User>> list(PageQueryDTO pageQueryDTO,
                                         @RequestParam(required = false) String role,
                                         @RequestParam(required = false) String keyword) {
        Page<User> page = new Page<>(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        if (role != null && !role.isEmpty()) {
            wrapper.eq(User::getRole, role);
        }

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(User::getUsername, keyword)
                    .or()
                    .like(User::getNickname, keyword));
        }

        wrapper.orderByDesc(User::getCreateTime);
        Page<User> userPage = userService.page(page, wrapper);

        userPage.getRecords().forEach(user -> user.setPassword(null));

        PageResult<User> pageResult = new PageResult<>(
                userPage.getRecords(),
                userPage.getTotal(),
                userPage.getCurrent(),
                userPage.getSize()
        );
        return Result.success(pageResult);
    }

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @GetMapping("/{id}")
    public Result<User> detail(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 修改后的用户信息
     */
    @PutMapping("/update")
    public Result<User> update(@RequestBody User user) {
        User existUser = userService.getById(user.getId());
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }

        user.setPassword(null);
        userService.updateById(user);

        User updated = userService.getById(user.getId());
        updated.setPassword(null);
        return Result.success("修改成功", updated);
    }

    /**
     * 修改用户密码
     *
     * @param params 密码参数，包含userId和newPassword
     * @return 修改结果
     */
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        String newPassword = params.get("newPassword").toString();

        User user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        user.setPassword(DigestUtil.md5Hex(newPassword));
        userService.updateById(user);
        return Result.success("密码修改成功", null);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        userService.removeById(id);
        return Result.success("删除成功", null);
    }
}
