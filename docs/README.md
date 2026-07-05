# 智学魔方 (zhixue_mofang)

## 项目简介

智学魔方是一款面向初中生的 AI 辅助学习平台，融合了多项智能化学习功能，帮助学生高效、有趣地学习初中课程。平台涵盖语文、数学、英语三大核心学科，提供文言文沉浸式剧场、AI 作文批改、错题智能分析、英语口语对练等特色功能。

### 核心功能模块

| 模块 | 功能描述 |
|------|----------|
| 文言文剧场 | 通过角色扮演方式沉浸式学习文言文经典篇目，支持对话交互 |
| AI 作文批改 | 提交作文后自动评分，从立意、结构、语言、逻辑四个维度进行 AI 批改 |
| 错题消灭 | 智能记录错题，AI 分析根因，支持归类整理和针对性练习 |
| 英语口语对练 | AI 驱动的口语练习场景，从发音、语调、流畅度三个维度评分 |
| 每日打卡 | 激励学习习惯养成，支持连续打卡统计和魔方碎片收集 |
| 社区互动 | 学生社区，支持发帖、评论、点赞，分享学习心得 |

---

## 技术栈

### 后端

- **框架**: Spring Boot 3.x
- **JDK**: Java 17
- **数据库**: MySQL 8.0
- **ORM**: MyBatis-Plus
- **安全**: Spring Security + BCrypt 密码加密
- **构建工具**: Maven

### 前端

- **框架**: Vue 3 + TypeScript
- **UI 组件库**: Element Plus
- **状态管理**: Pinia
- **路由**: Vue Router 4
- **HTTP 客户端**: Axios
- **构建工具**: Vite
- **Node.js**: 18+

---

## 环境要求

在部署和运行本项目之前，请确保以下环境已正确安装：

| 工具 | 版本要求 | 下载地址 |
|------|----------|----------|
| JDK | 17+ | https://adoptium.net/ |
| MySQL | 8.0+ | https://dev.mysql.com/downloads/mysql/ |
| Node.js | 18+ (前端编译需要) | https://nodejs.org/ |
| Maven | 3.8+ (或使用项目自带 mvnw) | https://maven.apache.org/download.cgi/ |

---

## 快速开始

### 方式一：一键部署（推荐）

确保环境准备好后，执行一键部署脚本：

**Windows BAT 脚本：**
```bat
cd deploy
deploy.bat
```

**PowerShell 脚本：**
```powershell
cd deploy
.\deploy.ps1
```

部署脚本会自动完成以下操作：
1. 检查 Java 17 和 MySQL 环境
2. 执行 SQL 初始化脚本创建数据库和测试数据
3. Maven 编译后端项目
4. npm 编译前端项目
5. 将前端产物复制到后端 static 目录
6. 启动后端服务

### 方式二：手动部署

#### 1. 初始化数据库

```bash
mysql -u root -p < sql/init.sql
```

如果需要修改数据库连接信息，请编辑 `sql/init.sql` 开头的连接配置。

#### 2. 启动后端

```bash
# 编译后端
mvn clean package -DskipTests

# 启动服务
java -jar target/zhixue-mofang.jar
```

#### 3. 启动前端（开发模式）

```bash
cd frontend
npm install
npm run dev
```

前端开发服务器默认运行在 http://localhost:5173，后端 API 代理到 http://localhost:8080。

#### 4. 访问系统

部署成功后，浏览器访问：
- **一体化部署**: http://localhost:8080
- **前后端分离模式**: 前端 http://localhost:5173，后端 API http://localhost:8080

---

## 测试账号

系统内置了两个测试账号：

| 用户名 | 密码 | 角色 | 昵称 |
|--------|------|------|------|
| admin | 123456 | 教师 (TEACHER) | 管理员 |
| student | 123456 | 学生 (STUDENT) | 小明同学 |

> 密码使用 BCrypt 加密存储，数据库中不可逆。

---

## 目录结构

```
zhixue-mofang/
├── deploy/                     # 部署脚本
│   ├── deploy.bat              # Windows BAT 一键部署脚本
│   └── deploy.ps1              # PowerShell 一键部署脚本
├── docs/                       # 项目文档
│   └── README.md               # 项目说明文档（本文件）
├── sql/                        # 数据库脚本
│   └── init.sql                # 数据库初始化脚本（建表 + 测试数据）
├── frontend/                   # 前端项目 (Vue 3)
│   ├── public/                 # 静态资源
│   ├── src/                    # 源代码
│   │   ├── api/                # API 接口封装
│   │   ├── assets/             # 资源文件
│   │   ├── components/         # 公共组件
│   │   ├── layouts/            # 布局组件
│   │   ├── router/             # 路由配置
│   │   ├── stores/             # Pinia 状态管理
│   │   ├── styles/             # 全局样式
│   │   ├── utils/              # 工具函数
│   │   └── views/              # 页面视图
│   ├── package.json            # 前端依赖配置
│   └── vite.config.ts          # Vite 配置
├── src/main/                   # 后端源代码 (Spring Boot)
│   ├── java/                   # Java 源代码
│   │   └── com/zhixue/mofang/
│   │       ├── config/         # 配置类
│   │       ├── controller/    # 控制器
│   │       ├── entity/         # 实体类
│   │       ├── mapper/         # MyBatis Mapper
│   │       ├── service/        # 业务逻辑层
│   │       ├── dto/            # 数据传输对象
│   │       ├── vo/             # 视图对象
│   │       ├── security/       # 安全配置
│   │       └── util/           # 工具类
│   └── resources/
│       ├── application.yml     # Spring Boot 配置文件
│       ├── static/             # 前端编译产物（部署时自动复制）
│       └── mapper/             # MyBatis XML 映射文件
├── pom.xml                     # Maven 项目配置
└── mvnw / mvnw.cmd            # Maven Wrapper
```

---

## API 接口文档

### 认证接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/auth/login | 用户登录 |
| POST | /api/auth/register | 用户注册 |
| POST | /api/auth/logout | 退出登录 |
| GET | /api/auth/userinfo | 获取当前用户信息 |

### 用户相关

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/user/profile/{userId} | 获取用户详情 |
| PUT | /api/user/profile | 更新用户信息 |
| PUT | /api/user/avatar | 更新头像 |
| PUT | /api/user/password | 修改密码 |

### 学生档案

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/student/profile | 获取我的学生档案 |
| PUT | /api/student/profile | 更新学生档案 |
| GET | /api/student/stats | 获取学习统计数据 |
| GET | /api/student/rank | 获取排行榜 |

### 文言文剧场

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/wenyanwen/list | 获取剧本列表 |
| GET | /api/wenyanwen/{id} | 获取剧本详情 |
| GET | /api/wenyanwen/{id}/scenes | 获取剧本场景列表 |
| POST | /api/wenyanwen/record | 记录学习进度 |

### 作文批改

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/essay/submit | 提交作文 |
| GET | /api/essay/list | 获取我的作文列表 |
| GET | /api/essay/{id} | 获取作文详情与批改结果 |
| GET | /api/essay/{id}/review | 获取 AI 批改详情 |

### 错题消灭

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/wrong/list | 获取错题列表 |
| POST | /api/wrong/add | 添加错题 |
| PUT | /api/wrong/{id} | 更新错题状态 |
| DELETE | /api/wrong/{id} | 删除错题 |
| GET | /api/wrong/analysis/{id} | 获取 AI 根因分析 |

### 词汇学习

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/vocabulary/list | 获取词汇列表 |
| GET | /api/vocabulary/{id} | 获取词汇详情 |
| POST | /api/vocabulary/quiz | 词汇测验 |

### 口语练习

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/oral/scenes | 获取练习场景列表 |
| POST | /api/oral/submit | 提交口语练习 |
| GET | /api/oral/history | 获取练习历史 |
| GET | /api/oral/{id}/feedback | 获取 AI 反馈详情 |

### 知识点

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/knowledge/list | 获取知识点列表 |
| GET | /api/knowledge/{id} | 获取知识点详情 |

### 学习记录

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/learning/records | 获取学习记录列表 |
| POST | /api/learning/record | 添加学习记录 |
| GET | /api/learning/daily | 获取每日学习统计 |

### 社区

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | /api/post/list | 获取帖子列表 |
| POST | /api/post | 发布帖子 |
| GET | /api/post/{id} | 获取帖子详情 |
| POST | /api/post/{id}/like | 点赞 |
| POST | /api/post/{id}/comment | 发表评论 |

### 每日打卡

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | /api/checkin | 每日打卡 |
| GET | /api/checkin/today | 获取今日打卡状态 |
| GET | /api/checkin/history | 获取打卡历史 |
| GET | /api/checkin/streak | 获取连续打卡天数 |

---

## 配置说明

### 后端配置 (application.yml)

主要配置项：

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/zhixue_mofang?useUnicode=true&characterEncoding=utf-8mb4&serverTimezone=Asia/Shanghai
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis-plus:
  mapper-locations: classpath:mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true
```

> 请根据实际环境修改数据库连接地址、用户名和密码。

### 数据库连接配置

在 `deploy/deploy.bat` 或 `deploy/deploy.ps1` 脚本顶部，可以修改 MySQL 连接参数：

```bat
set "MYSQL_HOST=127.0.0.1"
set "MYSQL_PORT=3306"
set "MYSQL_ROOT_USER=root"
set "MYSQL_ROOT_PASSWORD=root"
```

---

## 常见问题 FAQ

### Q1: 启动时报数据库连接失败

**原因**: MySQL 未启动或连接配置不正确。

**解决方案**:
1. 检查 MySQL 服务是否已启动
2. 确认 `application.yml` 中的数据库地址、端口、用户名、密码是否正确
3. 确认数据库 `zhixue_mofang` 已创建（执行 `sql/init.sql`）

### Q2: Maven 编译失败

**原因**: 依赖下载失败或网络问题。

**解决方案**:
1. 检查 Maven 配置的镜像源是否可用（建议使用阿里云镜像）
2. 清理本地仓库缓存后重试: `mvn clean`
3. 如果使用代理，请确保 Maven 代理配置正确

### Q3: 前端编译报错 "node_modules" 相关

**原因**: Node.js 版本不兼容或依赖安装不完整。

**解决方案**:
1. 确保 Node.js 版本 >= 18
2. 删除 `node_modules` 目录和 `package-lock.json`，重新执行 `npm install`
3. 清除 npm 缓存: `npm cache clean --force`

### Q4: 登录时提示密码错误

**原因**: 测试数据未正确插入。

**解决方案**:
1. 重新执行 `sql/init.sql` 初始化脚本
2. 确认数据库中 `user` 表有数据: `SELECT * FROM user;`
3. 密码为 "123456"，使用 BCrypt 加密存储

### Q5: 部署脚本中 MySQL 密码包含特殊字符

**原因**: BAT 脚本中特殊字符处理问题。

**解决方案**:
1. 使用 PowerShell 脚本 `deploy.ps1` 代替 BAT 脚本
2. 或手动执行 SQL: `mysql -u root -p < sql/init.sql`

### Q6: 前端页面空白或 404

**原因**: 前端产物未正确复制到后端 static 目录。

**解决方案**:
1. 手动执行: `xcopy /e /y frontend\dist\* src\main\resources\static\`
2. 确认 `src/main/resources/static/index.html` 存在
3. 检查 Spring Boot 静态资源配置

---

## 注意事项

1. **首次部署**请务必先执行 `sql/init.sql` 初始化数据库，否则系统无法正常启动。

2. **生产环境**部署时，请务必：
   - 修改数据库默认密码
   - 修改 Spring Security 配置，关闭开发模式下的宽松策略
   - 配置 HTTPS
   - 关闭 Swagger/API 文档的公开访问

3. **数据备份**: 建议定期备份 MySQL 数据库，使用 `mysqldump -u root -p zhixue_mofang > backup.sql`。

4. **前端独立部署**: 如果前端通过 Nginx 独立部署，需要配置反向代理将 `/api` 请求转发到后端服务。

5. **Nginx 配置参考** (前端独立部署时):
   ```nginx
   server {
       listen 80;
       server_name your-domain.com;

       location / {
           root /path/to/frontend/dist;
           try_files $uri $uri/ /index.html;
       }

       location /api/ {
           proxy_pass http://localhost:8080;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
       }
   }
   ```

6. **日志管理**: Spring Boot 默认日志输出到控制台，生产环境建议配置文件日志输出，并使用 logback-spring.xml 自定义日志格式和轮转策略。

---

## 数据库表说明

| 表名 | 说明 | 记录数（测试数据） |
|------|------|-------------------|
| user | 用户表 | 2 |
| student_profile | 学生档案表 | 1 |
| knowledge_point | 知识点表 | 15 |
| wrong_question | 错题表 | 5 |
| wenyanwen_script | 文言文剧本表 | 3 |
| wenyanwen_scene | 文言文场景表 | 9 |
| essay_submission | 作文提交表 | 5 |
| vocabulary | 词汇表 | 30 |
| oral_practice | 口语练习表 | 3 |
| learning_record | 学习记录表 | 10 |
| post | 社区帖子表 | 5 |
| post_comment | 评论表 | 8 |
| daily_checkin | 每日打卡表 | 15 |

---

## 许可证

本项目仅供学习和研究使用。
