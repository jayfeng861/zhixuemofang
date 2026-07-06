# 智学魔方 - 完整部署指南（前端 Netlify + 后端 Render）

## 问题背景

前端部署在 Netlify 后提示"网络连接异常"，是因为后端 API 服务没有部署到公网。
本文档指导你完成**前端（Netlify）+ 后端（Render）**的完整部署。

---

## 部署架构

```
┌─────────────────┐      ┌─────────────────┐
│  Netlify (前端)  │─────▶│  Render (后端)   │
│  Vue 3 + Vite   │      │  Spring Boot    │
│  静态站点        │      │  API 服务       │
│  xxx.netlify.app │      │  xxx.onrender.com│
└─────────────────┘      └─────────────────┘
```

---

## 第一部分：部署后端到 Render

### 步骤 1：注册 Render 账号

访问 https://render.com 并使用 GitHub 账号注册登录。

### 步骤 2：创建 Web Service

1. 点击 **"New +"** → **"Web Service"**
2. 选择 **"Build and deploy from a Git repository"**
3. 连接你的 GitHub 账号，选择仓库 `jayfeng861/zhixuemofang`
4. 点击 **"Continue"**

### 步骤 3：配置后端服务

| 配置项 | 值 | 说明 |
|--------|-----|------|
| **Name** | `zhixue-mofang-backend` | 服务名称 |
| **Region** | `Singapore` | 新加坡（国内访问较快） |
| **Runtime** | `Docker` | 使用 Docker 部署 |
| **Dockerfile Path** | `./Dockerfile` | 项目根目录的 Dockerfile |
| **Instance Type** | `Free` | 免费层 |

### 步骤 4：配置环境变量

在 **"Environment"** 部分添加以下变量：

| 变量名 | 值 | 说明 |
|--------|-----|------|
| `PORT` | `8080` | 服务端口 |
| `H2_CONSOLE_ENABLED` | `false` | 关闭 H2 控制台 |
| `JWT_SECRET` | `你的随机密钥字符串` | JWT 加密密钥（建议32位以上） |

> **提示**：免费层服务会在 15 分钟无请求后休眠，首次请求可能需要 30-60 秒唤醒。

### 步骤 5：部署

点击 **"Create Web Service"**，等待构建和部署完成。

部署成功后，你会获得一个后端地址，例如：
```
https://zhixue-mofang-backend.onrender.com
```

### 步骤 6：验证后端

访问以下地址验证后端是否正常：
```
https://zhixue-mofang-backend.onrender.com/api/v1/home/stats
```

如果返回 JSON 数据（如在线用户数、帖子数等），说明后端部署成功。

---

## 第二部分：配置 Netlify 前端

### 步骤 1：登录 Netlify

访问 https://app.netlify.com 登录。

### 步骤 2：进入站点设置

1. 选择你的 Netlify 站点
2. 点击 **"Site settings"** → **"Environment variables"**

### 步骤 3：添加环境变量

点击 **"Add a variable"** → **"Add a single variable"**：

| 变量名 | 值 | 说明 |
|--------|-----|------|
| `VITE_API_BASE_URL` | `https://zhixue-mofang-backend.onrender.com/api` | 后端 API 地址 |

> **重要**：将 `zhixue-mofang-backend.onrender.com` 替换为你在 Render 上获得的实际地址。

### 步骤 4：重新部署

1. 点击 **"Deploys"** 标签
2. 点击 **"Trigger deploy"** → **"Deploy site"**
3. 等待部署完成

### 步骤 5：验证前端

访问你的 Netlify 站点地址，尝试登录：
- 测试账号：`student001` / `123456`
- 管理员账号：`admin` / `123456`

---

## 方式二：使用 Netlify 代理（可选）

如果你不想在前端配置后端地址，可以使用 Netlify 的代理功能。

### 修改 netlify.toml

编辑项目根目录的 `netlify.toml`，取消 API 代理部分的注释，并替换为你的后端地址：

```toml
[[redirects]]
  from = "/api/*"
  to = "https://zhixue-mofang-backend.onrender.com/api/:splat"
  status = 200
  force = true
```

然后不需要设置 `VITE_API_BASE_URL` 环境变量，前端会通过 Netlify 代理访问后端。

---

## 常见问题

### Q: 登录还是提示"网络连接异常"？

A: 按以下顺序排查：

1. **检查后端是否正常运行**
   ```bash
   curl https://zhixue-mofang-backend.onrender.com/api/v1/home/stats
   ```
   如果无响应，可能是 Render 服务休眠中，等待 60 秒后重试。

2. **检查环境变量是否配置正确**
   - 在 Netlify 后台确认 `VITE_API_BASE_URL` 已正确设置
   - 地址必须包含 `/api` 后缀
   - 地址末尾不要有 `/`

3. **检查浏览器控制台**
   - 按 F12 打开开发者工具
   - 查看 Console 标签的错误信息
   - 查看 Network 标签的请求状态

4. **检查 CORS**
   - 后端已配置允许所有来源跨域
   - 如果仍有 CORS 错误，确认后端地址是否正确

### Q: Render 免费层服务休眠怎么办？

A: 免费层服务会在 15 分钟无请求后休眠。解决方案：
- **方案1**：升级到付费层（$7/月起）
- **方案2**：使用定时任务保持唤醒（如 UptimeRobot 每 10 分钟访问一次）
- **方案3**：接受休眠，首次访问等待 30-60 秒

### Q: 后端数据会丢失吗？

A: 当前使用 H2 内存数据库，**服务重启后数据会丢失**。如需持久化数据：
1. 在 Render 添加 PostgreSQL 数据库
2. 修改 `application.yml` 配置 PostgreSQL 连接
3. 修改 `pom.xml` 添加 PostgreSQL 驱动依赖

### Q: 如何更新部署？

A:
- **后端**：推送代码到 GitHub，Render 会自动重新部署
- **前端**：推送代码到 GitHub，Netlify 会自动重新部署

---

## 部署清单

- [ ] 后端已部署到 Render 并可正常访问
- [ ] Netlify 环境变量 `VITE_API_BASE_URL` 已正确配置
- [ ] Netlify 已重新部署
- [ ] 前端登录功能正常
- [ ] 前端注册功能正常
- [ ] 管理后台登录正常

---

## 技术支持

- Render 文档：https://render.com/docs
- Netlify 文档：https://docs.netlify.com
- Docker 文档：https://docs.docker.com
