package com.zhixue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * SPA单页应用路由配置
 * <p>
 * 将非API、非静态资源的请求全部转发到 index.html，
 * 支持Vue Router的history模式。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requested = location.createRelative(resourcePath);

                        // 如果请求的资源存在，直接返回（如JS/CSS/图片等静态文件）
                        if (requested.exists() && requested.isReadable()) {
                            return requested;
                        }

                        // 如果请求的是API路径，返回null让后续处理器处理
                        if (resourcePath.startsWith("api/")) {
                            return null;
                        }

                        // 其他所有请求返回 index.html，支持Vue Router history模式
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}
