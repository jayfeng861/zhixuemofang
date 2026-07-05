package com.zhixue.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.common.BusinessException;
import com.zhixue.config.JwtConfig;
import com.zhixue.dto.PageQueryDTO;
import com.zhixue.entity.Admin;
import com.zhixue.mapper.AdminMapper;
import com.zhixue.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员服务实现类
 * <p>
 * 实现管理员登录、增删改查等核心业务逻辑。
 * 密码使用MD5加密存储，登录成功返回JWT Token。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    private final JwtConfig jwtConfig;

    @Override
    public Map<String, Object> login(String username, String password) {
        Admin admin = getByUsername(username);
        if (admin == null) {
            throw new BusinessException("用户名不存在");
        }

        if (admin.getStatus() != null && admin.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }

        String encryptedPassword = DigestUtil.md5Hex(password);
        if (!encryptedPassword.equals(admin.getPassword())) {
            throw new BusinessException("密码错误");
        }

        String token = jwtConfig.generateAdminToken(admin.getId(), admin.getUsername());

        admin.setLastLoginTime(LocalDateTime.now());
        updateById(admin);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("adminId", admin.getId());
        result.put("username", admin.getUsername());
        result.put("nickname", admin.getNickname());
        result.put("avatar", admin.getAvatar());
        result.put("role", admin.getRole());
        result.put("email", admin.getEmail());

        return result;
    }

    @Override
    public Admin getByUsername(String username) {
        return lambdaQuery()
                .eq(Admin::getUsername, username)
                .last("LIMIT 1")
                .one();
    }

    @Override
    public Page<Admin> getAdminPage(PageQueryDTO pageQueryDTO) {
        Page<Admin> page = new Page<>(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize());
        return lambdaQuery()
                .orderByDesc(Admin::getCreateTime)
                .page(page);
    }

    @Override
    public Admin addAdmin(Admin admin) {
        if (getByUsername(admin.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }

        admin.setPassword(DigestUtil.md5Hex(admin.getPassword()));
        if (admin.getStatus() == null) {
            admin.setStatus(1);
        }
        if (admin.getRole() == null) {
            admin.setRole("admin");
        }
        save(admin);

        admin.setPassword(null);
        return admin;
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        Admin existAdmin = getById(admin.getId());
        if (existAdmin == null) {
            throw new BusinessException("管理员不存在");
        }

        if (admin.getUsername() != null && !existAdmin.getUsername().equals(admin.getUsername())) {
            Admin usernameAdmin = getByUsername(admin.getUsername());
            if (usernameAdmin != null && !usernameAdmin.getId().equals(admin.getId())) {
                throw new BusinessException("用户名已存在");
            }
        }

        // 如果传了密码，则加密保存
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            admin.setPassword(DigestUtil.md5Hex(admin.getPassword()));
        } else {
            admin.setPassword(null);
        }
        updateById(admin);

        Admin updated = getById(admin.getId());
        updated.setPassword(null);
        return updated;
    }

    @Override
    public void updatePassword(Long adminId, String oldPassword, String newPassword) {
        Admin admin = getById(adminId);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }

        String encryptedOldPassword = DigestUtil.md5Hex(oldPassword);
        if (!encryptedOldPassword.equals(admin.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        admin.setPassword(DigestUtil.md5Hex(newPassword));
        updateById(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        Admin admin = getById(id);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        removeById(id);
    }

    @Override
    public Map<String, Object> getAdminInfo(Long adminId) {
        Admin admin = getById(adminId);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }

        Map<String, Object> info = new HashMap<>();
        info.put("adminId", admin.getId());
        info.put("username", admin.getUsername());
        info.put("nickname", admin.getNickname());
        info.put("avatar", admin.getAvatar());
        info.put("role", admin.getRole());
        info.put("email", admin.getEmail());
        info.put("status", admin.getStatus());
        info.put("createTime", admin.getCreateTime());
        info.put("lastLoginTime", admin.getLastLoginTime());
        return info;
    }
}
