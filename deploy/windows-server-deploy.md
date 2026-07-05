# 智学魔方 - Windows 服务器部署完整指南

## 一、环境准备

### 1.1 必须安装的软件

| 软件 | 版本要求 | 下载地址 | 安装注意事项 |
|------|---------|---------|------------|
| **JDK** | 17+ | https://adoptium.net/ | 安装后配置 JAVA_HOME 环境变量 |
| **MySQL** | 8.0+ | https://dev.mysql.com/downloads/ | 记住 root 密码，端口默认 3306 |
| **Node.js** | 18+ LTS | https://nodejs.org/ | 选择 LTS 版本，勾选添加到 PATH |
| **Nginx** | 1.24+ (可选) | https://nginx.org/en/download.html | 用于反向代理和静态资源服务 |

### 1.2 环境验证命令

```cmd
java -version
mysql --version
node -v
npm -v
```

---

## 二、数据库初始化

### 2.1 执行SQL脚本

```cmd
:: 进入MySQL命令行
mysql -u root -p

:: 创建数据库并导入
source C:/zhixue-mofang/sql/init.sql

:: 验证表是否创建成功
USE zhixue_mofang;
SHOW TABLES;
```

### 2.2 修改后端数据库连接配置

编辑 `backend/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/zhixue_mofang?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root          # 改为你的MySQL用户名
    password: your_password # 改为你的MySQL密码
```

---

## 三、后端部署（Spring Boot）

### 3.1 编译打包

```cmd
cd C:\zhixue-mofang\backend
mvn clean package -DskipTests
```

打包完成后 jar 文件位于：`backend/target/zhixue-mofang-backend-1.0.0.jar`

### 3.2 方式一：直接启动（测试用）

```cmd
java -jar C:\zhixue-mofang\backend\target\zhixue-mofang-backend-1.0.0.jar
```

### 3.3 方式二：注册为 Windows 服务（生产推荐）

**使用 WinSW 注册服务：**

1. 下载 WinSW：https://github.com/winsw/winsw/releases
2. 重命名为 `zhixue-backend.exe`，放到 `C:\zhixue-mofang\backend\` 目录
3. 创建 `zhixue-backend.xml` 配置文件（同目录）：

```xml
<service>
  <id>ZhixueMofangBackend</id>
  <name>智学魔方后端服务</name>
  <description>智学魔方AI学习辅导系统 - Spring Boot后端服务</description>
  <executable>java</executable>
  <arguments>-Xms512m -Xmx1024m -jar "%BASE%\target\zhixue-mofang-backend-1.0.0.jar"</arguments>
  <logpath>%BASE%\logs</logpath>
  <log mode="roll-by-size">
    <sizeThreshold>10240</sizeThreshold>
    <keepFiles>8</keepFiles>
  </log>
  <onfailure action="restart" delay="10 sec"/>
  <onfailure action="restart" delay="20 sec"/>
  <onfailure action="none"/>
  <startmode>Automatic</startmode>
</service>
```

4. 注册并启动服务：

```cmd
:: 安装服务
C:\zhixue-mofang\backend\zhixue-backend.exe install

:: 启动服务
net start ZhixueMofangBackend

:: 查看服务状态
sc query ZhixueMofangBackend

:: 停止服务（需要时）
net stop ZhixueMofangBackend

:: 卸载服务（需要时）
C:\zhixue-mofang\backend\zhixue-backend.exe uninstall
```

---

## 四、前端部署（Vue3 + Nginx）

### 4.1 编译打包

```cmd
cd C:\zhixue-mofang\frontend
npm install
npm run build
```

打包完成后静态文件位于：`frontend/dist/`

### 4.2 方式一：Spring Boot 内嵌静态资源（简单部署）

```cmd
:: 将前端dist复制到后端static目录
xcopy /E /I /Y C:\zhixue-mofang\frontend\dist C:\zhixue-mofang\backend\src\main\resources\static

:: 重新编译后端
cd C:\zhixue-mofang\backend
mvn clean package -DskipTests

:: 启动后端（前后端统一端口8080）
java -jar target\zhixue-mofang-backend-1.0.0.jar
```

### 4.3 方式二：Nginx 反向代理（生产推荐）

1. 将 dist 目录复制到 Nginx 的 html 目录：

```cmd
xcopy /E /I /Y C:\zhixue-mofang\frontend\dist C:\nginx\html\zhixue
```

2. 修改 Nginx 配置文件 `conf/nginx.conf`：

```nginx
server {
    listen       80;
    server_name  localhost;  # 改为你的域名或IP

    # 前端静态资源
    location / {
        root   html/zhixue;
        index  index.html index.htm;
        try_files $uri $uri/ /index.html;  # SPA路由支持
    }

    # API反向代理到后端
    location /api/ {
        proxy_pass http://127.0.0.1:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_connect_timeout 60s;
        proxy_read_timeout 120s;
    }

    # Gzip压缩配置
    gzip on;
    gzip_types text/plain text/css application/json application/javascript text/xml application/xml text/javascript image/svg+xml;
    gzip_min_length 1024;
}
```

3. 启动/重启 Nginx：

```cmd
:: 检查配置
C:\nginx\nginx.exe -t

:: 启动Nginx
start C:\nginx\nginx.exe

:: 停止Nginx
C:\nginx\nginx.exe -s stop

:: 重新加载配置
C:\nginx\nginx.exe -s reload
```

---

## 五、Windows 防火墙配置

### 5.1 开放端口

```cmd
:: 开放80端口（HTTP）
netsh advfirewall firewall add rule name="HTTP" dir=in action=allow protocol=TCP localport=80

:: 开放8080端口（Spring Boot后端，如果不用Nginx代理）
netsh advfirewall firewall add rule name="SpringBoot" dir=in action=allow protocol=TCP localport=8080
```

---

## 六、生产环境注意事项

### 6.1 安全配置

1. **修改JWT密钥**：`application.yml` 中的 `jwt.secret` 必须改为随机长字符串
2. **修改数据库密码**：不要使用默认的 `root/root`
3. **关闭MySQL远程访问**：绑定 `127.0.0.1`，不暴露到公网
4. **HTTPS配置**：生产环境必须配置SSL证书，可在Nginx层配置

### 6.2 性能优化

1. **JVM参数**：生产环境建议 `-Xms1024m -Xmx2048m`
2. **MySQL优化**：调整 `innodb_buffer_pool_size` 为可用内存的 60-70%
3. **Nginx开启Gzip**：已在上方配置中包含

### 6.3 日志管理

- 后端日志目录：`logs/`（使用WinSW自动滚动）
- Nginx日志：`C:\nginx\logs\`
- 建议配置日志清理计划任务

### 6.4 自动启动

- 使用WinSW注册的Windows服务会自动开机启动
- Nginx可以添加到Windows启动文件夹实现开机启动

---

## 七、验证部署

### 7.1 检查清单

```cmd
:: 1. 检查后端API
curl http://localhost:8080/api/v1/auth/login

:: 2. 检查前端页面（Nginx方式）
curl http://localhost/

:: 3. 检查API代理（Nginx方式）
curl http://localhost/api/v1/auth/login
```

### 7.2 测试账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 教师/管理员 |
| student | 123456 | 学生 |
