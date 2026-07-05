package com.zhixue.config;

import com.zhixue.service.AdminService;
import com.zhixue.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

/**
 * Web MVC配置类
 * <p>
 * 注册JWT认证拦截器，对需要认证的接口进行Token验证。
 * 登录和注册接口不需要Token验证。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtConfig jwtConfig;
    private final UserService userService;
    private final AdminService adminService;

    /**
     * 注册拦截器
     * <p>
     * 添加JWT认证拦截器，排除登录和注册相关的路径。
     * </p>
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtAuthInterceptor(jwtConfig, userService))
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns(
                        "/api/v1/auth/login",
                        "/api/v1/auth/register",
                        "/api/v1/admin/login",
                        "/api/v1/admin/**",
                        "/api/v1/home/**",
                        "/api/v1/chinese/wenyanwen",
                        "/api/v1/english/vocabulary",
                        "/api/v1/community/posts",
                        "/api/v1/dashboard/ranking",
                        "/api/v1/system/announcements",
                        "/api/v1/system/feedback-types"
                );

        registry.addInterceptor(new AdminJwtAuthInterceptor(jwtConfig, adminService))
                .addPathPatterns("/api/v1/admin/**")
                .excludePathPatterns(
                        "/api/v1/admin/login"
                );
    }

    /**
     * JWT认证拦截器
     * <p>
     * 在请求处理之前验证JWT Token的有效性，
     * 并将用户ID存入请求属性中供后续使用。
     * </p>
     */
    @RequiredArgsConstructor
    public static class JwtAuthInterceptor implements HandlerInterceptor {

        private final JwtConfig jwtConfig;
        private final UserService userService;

        /**
         * 前置拦截：验证JWT Token
         * <p>
         * 从请求头中提取Token并验证其有效性。
         * 如果Token无效，直接返回401错误响应。
         * </p>
         *
         * @param request  HTTP请求对象
         * @param response HTTP响应对象
         * @param handler  处理器对象
         * @return 如果Token有效返回true继续处理，否则返回false
         * @throws IOException 如果写入响应时发生IO异常
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                return true;
            }

            String token = jwtConfig.getTokenFromRequest(request);
            if (token == null || !jwtConfig.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"未登录或Token已过期\",\"data\":null,\"timestamp\":" + System.currentTimeMillis() + "}");
                return false;
            }

            Long userId = jwtConfig.getUserIdFromToken(token);
            request.setAttribute("userId", userId);
            return true;
        }
    }

    /**
     * 管理员JWT认证拦截器
     * <p>
     * 在请求处理之前验证管理员JWT Token的有效性，
     * 并将管理员ID存入请求属性中供后续使用。
     * </p>
     */
    @RequiredArgsConstructor
    public static class AdminJwtAuthInterceptor implements HandlerInterceptor {

        private final JwtConfig jwtConfig;
        private final AdminService adminService;

        /**
         * 前置拦截：验证管理员JWT Token
         * <p>
         * 从请求头中提取Token并验证其有效性，
         * 同时验证Token类型是否为管理员。
         * 如果Token无效，直接返回401错误响应。
         * </p>
         *
         * @param request  HTTP请求对象
         * @param response HTTP响应对象
         * @param handler  处理器对象
         * @return 如果Token有效返回true继续处理，否则返回false
         * @throws IOException 如果写入响应时发生IO异常
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                return true;
            }

            String token = jwtConfig.getTokenFromRequest(request);
            if (token == null || !jwtConfig.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"未登录或Token已过期\",\"data\":null,\"timestamp\":" + System.currentTimeMillis() + "}");
                return false;
            }

            if (!jwtConfig.isAdminToken(token)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":403,\"message\":\"无权限访问，请使用管理员账号登录\",\"data\":null,\"timestamp\":" + System.currentTimeMillis() + "}");
                return false;
            }

            Long adminId = jwtConfig.getAdminIdFromToken(token);
            request.setAttribute("adminId", adminId);
            return true;
        }
    }
}
