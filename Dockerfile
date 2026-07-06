# 智学魔方后端 Dockerfile
# 多阶段构建，减小最终镜像大小

# 阶段1: 构建阶段
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# 先复制 pom.xml，利用 Docker 缓存加速依赖下载
COPY backend/pom.xml .
RUN mvn dependency:go-offline -B

# 复制源代码并构建
COPY backend/src ./src
RUN mvn clean package -DskipTests -B

# 阶段2: 运行阶段
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 复制构建产物
COPY --from=builder /build/target/zhixue-mofang-backend-1.0.0.jar app.jar

# 暴露端口（Render 会通过 PORT 环境变量覆盖）
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -q -O- http://localhost:8080/api/v1/home/stats || exit 1
