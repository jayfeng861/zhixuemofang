package com.zhixue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.dto.PageQueryDTO;
import com.zhixue.entity.Admin;

import java.util.Map;

/**
 * 管理员服务接口
 * <p>
 * 提供管理员登录、增删改查等业务方法。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
public interface AdminService extends IService<Admin> {

    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果，包含token和管理员基本信息
     */
    Map<String, Object> login(String username, String password);

    /**
     * 根据用户名查询管理员
     *
     * @param username 用户名
     * @return 管理员实体，不存在返回null
     */
    Admin getByUsername(String username);

    /**
     * 分页查询管理员列表
     *
     * @param pageQueryDTO 分页参数
     * @return 分页结果
     */
    Page<Admin> getAdminPage(PageQueryDTO pageQueryDTO);

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @return 添加后的管理员信息
     */
    Admin addAdmin(Admin admin);

    /**
     * 修改管理员信息
     *
     * @param admin 管理员信息
     * @return 修改后的管理员信息
     */
    Admin updateAdmin(Admin admin);

    /**
     * 修改管理员密码
     *
     * @param adminId   管理员ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(Long adminId, String oldPassword, String newPassword);

    /**
     * 删除管理员
     *
     * @param id 管理员ID
     */
    void deleteAdmin(Long id);

    /**
     * 获取管理员信息（不含密码）
     *
     * @param adminId 管理员ID
     * @return 管理员基本信息Map
     */
    Map<String, Object> getAdminInfo(Long adminId);
}
