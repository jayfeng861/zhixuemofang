# 智学魔方 - Netlify 部署指南

## 项目概述

智学魔方是一个基于 Vue 3 + Spring Boot 的初中AI学习辅导平台。
本指南介绍如何将前端项目部署到 Netlify 平台。

> **注意**：Netlify 主要用于部署前端静态站点，后端 Spring Boot 服务需单独部署到其他平台（如 Railway、Render、阿里云等）。

---

## 部署架构

```
┌─────────────────┐      ┌─────────────────┐
│  Netlify (前端)  │─────▶│  后端服务器      │
│  Vue 3 + Vite   │      │  Spring Boot    │
│  静态站点        │      │  API 服务       │
└─────────────────┘      └─────────────────┘
```

---

## 部署准备

### 1. 后端部署

在部署前端之前，请确保后端 API 服务已经部署并可以正常访问。

推荐的后端部署平台：
- **Railway** - https://railway.app （支持 Spring Boot，免费额度）
- **Render** - https://render.com （支持 Java，有免费层）
- **阿里云** - https://www.aliyun.com （国内访问快）
- **腾讯云** - https://cloud.tencent.com （国内访问快）

后端部署完成后，记录 API 基础地址，例如：
```
https://your-api-domain.com/api
```

### 2. GitHub 仓库

确保项目代码已推送到 GitHub 仓库：
- 仓库地址：`https://github.com/jayfeng861/zhixuemofang`

---

## 部署步骤

### 方法一：通过 Netlify 网站部署（推荐）

#### 步骤 1：登录 Netlify

访问 https://app.netlify.com 并使用 GitHub 账号登录。

#### 步骤 2：创建新站点

1. 点击 **"Add new site"** → **"Import an existing project"**
2. 在 **"Connect to Git provider"** 中选择 **GitHub**
3. 授权 Netlify 访问你的仓库
4. 选择你的仓库 `zhixuemofang`

#### 步骤 3：配置部署

在 **"Site settings"** 页面：

| 配置项 | 值 | 说明 |
|--------|-----|------|
| **Site name** | `zhixue-mofang` | 站点名称（可自定义） |
| **Branch to deploy** | `master` | 部署分支 |
| **Base directory** | `frontend` | 前端项目所在目录 |
| **Build command** | `npm install && npm run build` | 构建命令 |
| **Publish directory** | `frontend/dist` | 构建输出目录 |

> **提示**：如果项目根目录有 `netlify.toml` 文件，Netlify 会自动读取配置，无需手动填写上述信息。

#### 步骤 4：配置环境变量

在 **"Environment variables"** 部分点击 **"Add environment variable"**：

| 变量名 | 值 | 说明 |
|--------|-----|------|
| `VITE_API_BASE_URL` | `https://your-api-domain.com/api` | 后端 API 地址 |
| `NODE_VERSION` | `20` | Node.js 版本 |

> 将 `https://your-api-domain.com/api` 替换为你的后端 API 实际地址。

#### 步骤 5：部署

点击 **"Deploy site"** 按钮，等待部署完成。

部署成功后，你将获得一个 Netlify 分配的域名，例如：
```
https://zhixue-mofang.netlify.app
```

---

### 方法二：通过 Netlify CLI 部署

#### 步骤 1：安装 Netlify CLI

```bash
npm install -g netlify-cli
```

#### 步骤 2：登录 Netlify

```bash
netlify login
```

按照提示在浏览器中完成授权。

#### 步骤 3：初始化项目（首次部署）

在项目根目录执行：

```bash
netlify init
```

按照提示完成配置：
- What would you like to do? → `Create & configure a new site`
- Team? → 选择你的团队
- Site name? → `zhixue-mofang`（或自定义）

#### 步骤 4：设置环境变量

```bash
netlify env set VITE_API_BASE_URL "https://your-api-domain.com/api"
```

#### 步骤 5：部署

```bash
# 部署预览
netlify deploy

# 部署生产
netlify deploy --prod
```

---

## 配置文件说明

### netlify.toml

项目根目录的 `netlify.toml` 包含以下配置：

```toml
[build]
  base = "frontend"
  command = "npm install && npm run build"
  publish = "dist"

[build.environment]
  NODE_VERSION = "20"
  NPM_FLAGS = "--legacy-peer-deps"

# SPA 路由支持 - 所有页面重写到 index.html
[[redirects]]
  from = "/*"
  to = "/index.html"
  status = 200
  conditions = {Path = ["!/assets/*", "!/api/*", "!/favicon.svg", "!/*.js", "!/*.css", "!/*.png", "!/*.jpg", "!/*.svg", "!/*.ico", "!/*.json", "!/*.woff", "!/*.woff2", "!/*.ttf"]}

# API 代理 - 将 /api/* 请求代理到后端服务器
[[redirects]]
  from = "/api/*"
  to = "https://zhixue-mofang-api.example.com/api/:splat"
  status = 200
  force = true

# 静态资源缓存策略
[[headers]]
  for = "/assets/*"
  [headers.values]
    Cache-Control = "public, max-age=31536000, immutable"
```

#### 关键配置说明

1. **base**：前端项目所在目录（`frontend`）
2. **command**：构建命令，安装依赖并构建
3. **publish**：构建输出目录（`dist`，相对于 `base`）
4. **redirects**：
   - `/api/*` 请求代理到后端 API（可选，也可以直接配置 `VITE_API_BASE_URL`）
   - 其他路径重写到 `index.html`（SPA 路由支持）
5. **headers**：静态资源缓存策略和安全头设置

### 环境变量文件

- `frontend/.env.development` - 开发环境配置
- `frontend/.env.production` - 生产环境配置模板

#### 可用环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| `VITE_API_BASE_URL` | API 基础地址 | `/api` |

---

## 自定义域名

### 添加自定义域名

1. 在 Netlify 站点页面，点击 **"Domain settings"** → **"Add a domain"**
2. 输入你的域名，例如：`zhixuemofang.com`
3. 点击 **"Verify"** → **"Add domain"**

### DNS 配置

在你的域名服务商处添加 DNS 记录：

| 类型 | 主机记录 | 记录值 |
|------|----------|--------|
| CNAME | `www` | `zhixue-mofang.netlify.app` |
| A | `@` | `75.2.60.5` |

或者直接使用 Netlify DNS：
1. 在域名服务商处将 Nameserver 改为 Netlify 提供的 DNS 服务器
2. 在 Netlify 中自动管理 DNS 记录

等待 DNS 生效后，Netlify 会自动配置 SSL 证书（Let's Encrypt）。

---

## 后端部署参考

### 方案一：Railway（推荐新手）

1. 访问 https://railway.app
2. 使用 GitHub 登录
3. 点击 **"New Project"** → **"Deploy from GitHub repo"**
4. 选择你的仓库
5. 配置构建命令：
   ```
   mvn clean package -DskipTests
   ```
6. 配置启动命令：
   ```
   java -jar target/zhixue-mofang-1.0.0.jar
   ```
7. 配置环境变量（数据库连接等）
8. 点击 **"Deploy"**

### 方案二：Render

1. 访问 https://render.com
2. 使用 GitHub 登录
3. 点击 **"New"** → **"Web Service"**
4. 选择你的仓库
5. 配置：
   - **Runtime**: `Java`
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/zhixue-mofang-1.0.0.jar`
6. 点击 **"Create Web Service"**

---

## 本地构建验证

在部署前，可以先在本地构建验证：

```bash
# 进入前端目录
cd frontend

# 安装依赖
npm install

# 生产构建
npm run build

# 本地预览构建结果
npm run preview
```

构建成功后，`dist` 目录下生成的文件就是可以直接部署的静态文件。

---

## 自动部署配置

### 连续部署（CD）

Netlify 默认开启了连续部署：
1. 当你推送代码到 GitHub 的 `master` 分支时，Netlify 会自动触发构建
2. 构建成功后自动部署到生产环境
3. 构建失败会发送邮件通知

### 部署预览

Netlify 会为每个 Pull Request 自动创建部署预览：
- 在 PR 页面会显示预览链接
- 可以在合并前预览变更效果
- 不会影响生产环境

### 部署通知

可以在 **"Site settings"** → **"Build & deploy"** → **"Deploy notifications"** 中配置：
- 邮件通知
- Slack 通知
- Webhook 通知

---

## 常见问题

### Q: 部署后刷新页面显示 404？

A: 这是 SPA 应用的常见问题。`netlify.toml` 中已经配置了 redirects 规则，将所有路径重写到 `index.html`。如果仍然有问题，请检查：
1. `netlify.toml` 是否在项目根目录
2. redirects 配置是否正确
3. 在 Netlify 后台检查 **"Redirects"** 是否生效

### Q: API 请求失败？

A: 检查以下几点：
1. 后端服务是否正常运行
2. `VITE_API_BASE_URL` 环境变量是否正确配置
3. 后端是否配置了 CORS 跨域支持
4. `netlify.toml` 中的 API 代理地址是否正确

### Q: 构建失败？

A: 常见原因：
1. **依赖安装失败** - 检查 `package.json` 依赖是否正确，尝试添加 `NPM_FLAGS = "--legacy-peer-deps"`
2. **TypeScript 类型错误** - 运行 `npm run build` 查看具体错误
3. **Node 版本不兼容** - 在 `netlify.toml` 中设置 `NODE_VERSION = "20"`
4. **内存不足** - 在 Netlify 后台设置 `NODE_OPTIONS = "--max_old_space_size=4096"`

### Q: 如何更新部署？

A:
- **自动部署**：推送代码到 GitHub 主分支，Netlify 会自动触发部署
- **手动部署**：在 Netlify 站点页面点击 **"Deploys"** → **"Trigger deploy"** → **"Deploy site"**
- **CLI 部署**：执行 `netlify deploy --prod`

### Q: 如何回滚到之前的版本？

A: 在 Netlify 站点页面：
1. 点击 **"Deploys"**
2. 找到要回滚的部署
3. 点击 **"Publish deploy"** 即可回滚

### Q: 如何查看部署日志？

A: 在 Netlify 站点页面：
1. 点击 **"Deploys"**
2. 点击最新的部署
3. 在 **"Deploy log"** 中查看详细日志

---

## 部署清单

部署前请确认：

- [ ] 后端 API 已部署并可正常访问
- [ ] 代码已推送到 GitHub 仓库
- [ ] `netlify.toml` 配置正确
- [ ] 环境变量已准备好
- [ ] 本地构建测试通过
- [ ] 自定义域名已准备好（可选）

---

## Netlify 免费额度说明

| 资源 | 免费额度 |
|------|----------|
| 带宽 | 100 GB/月 |
| 构建分钟数 | 300 分钟/月 |
| 并发构建数 | 1 |
| 页面访问量 | 无限制 |
| 表单提交 | 100 次/月 |
| 函数调用 | 125,000 次/月 |

> 对于中小型教育网站，免费额度完全够用。

---

## 技术支持

如有部署问题，请参考：
- Netlify 官方文档：https://docs.netlify.com
- Vite 部署指南：https://vitejs.dev/guide/static-deploy.html
- Vue Router History 模式：https://router.vuejs.org/guide/essentials/history-mode.html
- Netlify CLI 文档：https://docs.netlify.com/cli/get-started/
