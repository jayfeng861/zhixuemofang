# 智学魔方后端 Dockerfile
# 多阶段构建，减小最终镜像大小

# 阶段1: 构建阶段
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# 复制整个 backend 目录（包含 pom.xml 和 src）
COPY backend/ .

# 构建项目（跳过测试）
RUN mvn clean package -DskipTests -B

# 阶段2: 运行阶段
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 安装 curl 用于健康检查
RUN apk add --no-cache curl

# 复制构建产物
COPY --from=builder /build/target/zhixue-mofang-backend-1.0.0.jar app.jar

# 暴露端口（Render 会通过 PORT 环境变量覆盖）
EXPOSE 8080

# 启动命令（使用 exec 模式，支持优雅关闭）
ENTRYPOINT ["java", "-jar", "app.jar"]

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/api/v1/home/stats || exit 1
