package com.zhixue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置类
 * <p>
 * 配置全局CORS跨域支持，允许前端跨域请求后端API。
 * 开发环境下允许所有来源的请求。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Configuration
public class CorsConfig {

    /**
     * 创建跨域过滤器Bean
     * <p>
     * 允许所有HTTP方法、所有请求头、所有来源的跨域请求，
     * 并允许携带认证信息（Cookie等）。
     * </p>
     *
     * @return 配置好的CorsFilter实例
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有来源
        config.addAllowedOriginPattern("*");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 允许所有HTTP方法
        config.addAllowedMethod("*");
        // 允许携带凭证
        config.setAllowCredentials(true);
        // 预检请求缓存时间（秒）
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
