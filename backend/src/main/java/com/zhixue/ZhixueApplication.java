package com.zhixue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 智学魔方应用启动类
 * <p>
 * 初中AI学习辅导系统后端主入口，基于Spring Boot 3.x构建。
 * 集成MyBatis-Plus进行数据持久化操作。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.zhixue.mapper")
public class ZhixueApplication {

    /**
     * 应用程序主入口方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ZhixueApplication.class, args);
        System.out.println("========================================");
        System.out.println("  智学魔方后端服务启动成功！");
        System.out.println("  访问地址: http://localhost:8080");
        System.out.println("========================================");
    }
}
