package com.zhixue.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixue.common.BusinessException;
import com.zhixue.config.JwtConfig;
import com.zhixue.dto.LoginDTO;
import com.zhixue.dto.RegisterDTO;
import com.zhixue.entity.StudentProfile;
import com.zhixue.entity.User;
import com.zhixue.mapper.UserMapper;
import com.zhixue.service.StudentProfileService;
import com.zhixue.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现类
 * <p>
 * 实现用户注册、登录、信息查询等核心业务逻辑。
 * 密码使用MD5加密存储，登录成功返回JWT Token。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final JwtConfig jwtConfig;
    private final StudentProfileService studentProfileService;

    /**
     * 用户登录
     * <p>
     * 验证用户名和密码，登录成功后生成JWT Token返回。
     * 密码使用MD5加密比对。
     * </p>
     *
     * @param loginDTO 登录请求参数，包含用户名和密码
     * @return 登录结果Map，包含token和用户基本信息
     * @throws BusinessException 用户名不存在或密码错误时抛出
     */
    @Override
    public Map<String, Object> login(LoginDTO loginDTO) {
        // 查询用户
        User user = getByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户名不存在");
        }

        // 验证密码（MD5加密比对）
        String encryptedPassword = DigestUtil.md5Hex(loginDTO.getPassword());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 生成JWT Token
        String token = jwtConfig.generateToken(user.getId(), user.getUsername());

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        result.put("nickname", user.getNickname());
        result.put("avatar", user.getAvatar());
        result.put("role", user.getRole());
        result.put("grade", user.getGrade());

        return result;
    }

    /**
     * 用户注册
     * <p>
     * 创建新用户账号，同时初始化学生档案。
     * 密码使用MD5加密存储。
     * </p>
     *
     * @param registerDTO 注册请求参数
     * @return 注册成功的用户实体
     * @throws BusinessException 用户名已存在时抛出
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        if (getByUsername(registerDTO.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(DigestUtil.md5Hex(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname());
        user.setGrade(registerDTO.getGrade());
        user.setRole("student");
        user.setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=" + registerDTO.getUsername());

        save(user);

        // 初始化学生档案
        StudentProfile profile = new StudentProfile();
        profile.setUserId(user.getId());
        profile.setStudyDays(0);
        profile.setTotalPoints(0);
        studentProfileService.save(profile);

        // 清除密码后返回
        user.setPassword(null);
        return user;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体，不存在返回null
     */
    @Override
    public User getByUsername(String username) {
        return lambdaQuery()
                .eq(User::getUsername, username)
                .last("LIMIT 1")
                .one();
    }

    /**
     * 根据用户ID查询用户信息（不含密码）
     *
     * @param userId 用户ID
     * @return 用户基本信息Map
     * @throws BusinessException 用户不存在时抛出
     */
    @Override
    public Map<String, Object> getUserInfo(Long userId) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        Map<String, Object> info = new HashMap<>();
        info.put("userId", user.getId());
        info.put("username", user.getUsername());
        info.put("nickname", user.getNickname());
        info.put("avatar", user.getAvatar());
        info.put("role", user.getRole());
        info.put("grade", user.getGrade());
        info.put("createTime", user.getCreateTime());
        return info;
    }
}
