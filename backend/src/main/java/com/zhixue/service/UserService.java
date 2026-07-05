package com.zhixue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixue.dto.LoginDTO;
import com.zhixue.dto.RegisterDTO;
import com.zhixue.entity.User;

import java.util.Map;

/**
 * 用户服务接口
 * <p>
 * 提供用户注册、登录、信息查询等业务方法。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param loginDTO 登录请求参数
     * @return 登录结果，包含token和用户基本信息
     */
    Map<String, Object> login(LoginDTO loginDTO);

    /**
     * 用户注册
     *
     * @param registerDTO 注册请求参数
     * @return 注册成功的用户信息
     */
    User register(RegisterDTO registerDTO);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体，不存在返回null
     */
    User getByUsername(String username);

    /**
     * 根据用户ID查询用户信息（不含密码）
     *
     * @param userId 用户ID
     * @return 用户基本信息Map
     */
    Map<String, Object> getUserInfo(Long userId);
}
