FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

COPY backend/ .

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN apk add --no-cache curl

COPY --from=builder /build/target/zhixue-mofang-backend-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/api/v1/home/stats || exit 1
