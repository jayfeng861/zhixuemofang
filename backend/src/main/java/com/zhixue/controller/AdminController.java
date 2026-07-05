package com.zhixue.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixue.common.PageResult;
import com.zhixue.common.Result;
import com.zhixue.dto.PageQueryDTO;
import com.zhixue.entity.Admin;
import com.zhixue.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员控制器
 * <p>
 * 处理管理员登录、增删改查等请求。
 * 路径前缀：/api/v1/admin
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 管理员登录
     * <p>
     * 接收用户名和密码进行身份验证，成功后返回JWT Token和管理员基本信息。
     * </p>
     *
     * @param params 登录请求参数，包含username和password
     * @return 登录结果，包含token和管理员信息
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return Result.success("登录成功", adminService.login(username, password));
    }

    /**
     * 获取管理员列表（分页）
     *
     * @param pageQueryDTO 分页参数
     * @return 分页后的管理员列表
     */
    @GetMapping("/list")
    public Result<PageResult<Admin>> list(PageQueryDTO pageQueryDTO) {
        Page<Admin> page = adminService.getAdminPage(pageQueryDTO);
        PageResult<Admin> pageResult = new PageResult<>(
                page.getRecords(),
                page.getTotal(),
                page.getCurrent(),
                page.getSize()
        );
        return Result.success(pageResult);
    }

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @return 添加后的管理员信息
     */
    @PostMapping("/add")
    public Result<Admin> add(@RequestBody Admin admin) {
        return Result.success("添加成功", adminService.addAdmin(admin));
    }

    /**
     * 修改管理员信息
     *
     * @param admin 管理员信息
     * @return 修改后的管理员信息
     */
    @PutMapping("/update")
    public Result<Admin> update(@RequestBody Admin admin) {
        return Result.success("修改成功", adminService.updateAdmin(admin));
    }

    /**
     * 修改管理员密码
     *
     * @param adminId 从JWT Token中解析出的管理员ID
     * @param params  密码参数，包含oldPassword和newPassword
     * @return 修改结果
     */
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestAttribute("adminId") Long adminId, @RequestBody Map<String, String> params) {
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        adminService.updatePassword(adminId, oldPassword, newPassword);
        return Result.success("密码修改成功", null);
    }

    /**
     * 删除管理员
     *
     * @param id 管理员ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return Result.success("删除成功", null);
    }

    /**
     * 获取当前管理员信息
     *
     * @param adminId 从JWT Token中解析出的管理员ID
     * @return 当前管理员的基本信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo(@RequestAttribute("adminId") Long adminId) {
        return Result.success(adminService.getAdminInfo(adminId));
    }
}
