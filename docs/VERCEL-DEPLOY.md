# 智学魔方 - Vercel 部署指南

## 项目概述

智学魔方是一个基于 Vue 3 + Spring Boot 的初中AI学习辅导平台。
本指南介绍如何将前端项目部署到 Vercel 平台。

> **注意**：Vercel 主要用于部署前端静态站点，后端 Spring Boot 服务需单独部署到其他平台（如 Railway、Render、阿里云等）。

---

## 部署架构

```
┌─────────────────┐      ┌─────────────────┐
│   Vercel (前端)  │─────▶│  后端服务器      │
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

### 方法一：通过 Vercel 网站部署（推荐）

#### 步骤 1：登录 Vercel

访问 https://vercel.com 并使用 GitHub 账号登录。

#### 步骤 2：导入项目

1. 点击 **"Add New..."** → **"Project"**
2. 在 **"Import Git Repository"** 中选择你的仓库 `zhixuemofang`
3. 点击 **"Import"**

#### 步骤 3：配置项目

在 **"Configure Project"** 页面：

| 配置项 | 值 | 说明 |
|--------|-----|------|
| **Project Name** | `zhixue-mofang` | 项目名称 |
| **Framework Preset** | `Vite` | 自动识别 |
| **Root Directory** | 留空（仓库根目录） | 根目录有 vercel.json |
| **Build Command** | `cd frontend && npm install && npm run build` | 构建命令 |
| **Output Directory** | `frontend/dist` | 输出目录 |

#### 步骤 4：配置环境变量

在 **"Environment Variables"** 部分添加：

| 变量名 | 值 | 环境 |
|--------|-----|------|
| `VITE_API_BASE_URL` | `https://your-api-domain.com/api` | Production |

> 将 `https://your-api-domain.com/api` 替换为你的后端 API 实际地址。

#### 步骤 5：部署

点击 **"Deploy"** 按钮，等待部署完成。

部署成功后，你将获得一个 Vercel 分配的域名，例如：
```
https://zhixuemofang.vercel.app
```

---

### 方法二：通过 Vercel CLI 部署

#### 步骤 1：安装 Vercel CLI

```bash
npm install -g vercel
```

#### 步骤 2：登录 Vercel

```bash
vercel login
```

#### 步骤 3：部署

在项目根目录执行：

```bash
vercel --prod
```

按照提示完成配置：
- Set up and deploy? `Y`
- Which scope? 选择你的账号
- Link to existing project? `N`
- What's your project's name? `zhixue-mofang`
- In which directory is your code located? `./` (直接回车)
- Want to modify these settings? `N`

#### 步骤 4：设置环境变量

```bash
vercel env add VITE_API_BASE_URL
```

输入环境变量值，然后重新部署：

```bash
vercel --prod
```

---

## 配置文件说明

### vercel.json

项目根目录的 `vercel.json` 包含以下配置：

```json
{
  "buildCommand": "cd frontend && npm install && npm run build",
  "outputDirectory": "frontend/dist",
  "framework": "vite",
  "rewrites": [
    {
      "source": "/api/:path*",
      "destination": "https://zhixue-mofang-api.example.com/api/:path*"
    },
    {
      "source": "/((?!api/|assets/|favicon\\.svg|.*\\..*).*)",
      "destination": "/index.html"
    }
  ]
}
```

#### 关键配置说明

1. **buildCommand**：构建命令，进入 frontend 目录安装依赖并构建
2. **outputDirectory**：构建输出目录
3. **rewrites**：
   - `/api/*` 请求代理到后端 API（可选，也可以直接配置 `VITE_API_BASE_URL`）
   - 其他路径重写到 `index.html`（SPA 路由支持）

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

1. 在 Vercel 项目页面，点击 **"Settings"** → **"Domains"**
2. 输入你的域名，例如：`zhixuemofang.com`
3. 点击 **"Add"**

### DNS 配置

在你的域名服务商处添加 DNS 记录：

| 类型 | 主机记录 | 记录值 |
|------|----------|--------|
| CNAME | `www` | `cname.vercel-dns.com` |
| A | `@` | `76.76.21.21` |

等待 DNS 生效后，Vercel 会自动配置 SSL 证书。

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

## 常见问题

### Q: 部署后刷新页面显示 404？

A: 这是 SPA 应用的常见问题。`vercel.json` 中已经配置了 rewrites 规则，将所有路径重写到 `index.html`。如果仍然有问题，请检查 `vercel.json` 是否正确配置。

### Q: API 请求失败？

A: 检查以下几点：
1. 后端服务是否正常运行
2. `VITE_API_BASE_URL` 环境变量是否正确配置
3. 后端是否配置了 CORS 跨域支持

### Q: 构建失败？

A: 常见原因：
1. 依赖安装失败 - 检查 `package.json` 依赖是否正确
2. TypeScript 类型错误 - 运行 `npm run build` 查看具体错误
3. Node 版本不兼容 - Vercel 默认使用 Node 18，如需指定版本可以在 `package.json` 中添加：
   ```json
   {
     "engines": {
       "node": "20.x"
     }
   }
   ```

### Q: 如何更新部署？

A:
- **自动部署**：推送代码到 GitHub 主分支，Vercel 会自动触发部署
- **手动部署**：在 Vercel 项目页面点击 **"Redeploy"**

---

## 部署清单

部署前请确认：

- [ ] 后端 API 已部署并可正常访问
- [ ] 代码已推送到 GitHub 仓库
- [ ] `vercel.json` 配置正确
- [ ] 环境变量已准备好
- [ ] 本地构建测试通过
- [ ] 自定义域名已准备好（可选）

---

## 技术支持

如有部署问题，请参考：
- Vercel 官方文档：https://vercel.com/docs
- Vite 部署指南：https://vitejs.dev/guide/static-deploy.html
- Vue Router History 模式：https://router.vuejs.org/guide/essentials/history-mode.html
