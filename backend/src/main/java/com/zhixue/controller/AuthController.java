package com.zhixue.controller;

import com.zhixue.common.Result;
import com.zhixue.dto.LoginDTO;
import com.zhixue.dto.RegisterDTO;
import com.zhixue.entity.User;
import com.zhixue.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 认证控制器
 * <p>
 * 处理用户登录、注册和获取当前用户信息请求。
 * 路径前缀：/api/v1/auth
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 用户登录
     * <p>
     * 接收用户名和密码进行身份验证，成功后返回JWT Token和用户基本信息。
     * </p>
     *
     * @param loginDTO 登录请求参数
     * @return 登录结果，包含token和用户信息
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginDTO loginDTO) {
        return Result.success("登录成功", userService.login(loginDTO));
    }

    /**
     * 用户注册
     * <p>
     * 创建新用户账号，注册成功后自动初始化学生档案。
     * </p>
     *
     * @param registerDTO 注册请求参数
     * @return 注册成功的用户信息
     */
    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return Result.success("注册成功", userService.register(registerDTO));
    }

    /**
     * 获取当前用户信息
     * <p>
     * 通过JWT Token识别当前登录用户，返回其基本信息。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @return 当前用户的基本信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestAttribute("userId") Long userId) {
        return Result.success(userService.getUserInfo(userId));
    }
}
