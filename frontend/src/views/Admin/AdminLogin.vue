<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAdminStore } from '@/store'
import { ElMessage } from 'element-plus'
import { User, Lock, ArrowRight, Setting, UserFilled } from '@element-plus/icons-vue'

const router = useRouter()
const adminStore = useAdminStore()

const loginForm = ref({
  username: '',
  password: ''
})
const loading = ref(false)

async function handleLogin() {
  if (!loginForm.value.username || !loginForm.value.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }
  loading.value = true
  try {
    await adminStore.login(loginForm.value.username, loginForm.value.password)
    ElMessage.success('登录成功，欢迎回来！')
    router.push('/admin/dashboard')
  } catch (e: any) {
    const msg = (e?.response?.data?.message) || '登录失败，请重试'
    ElMessage.error(msg)
  } finally {
    loading.value = false
  }
}

function goHome() {
  router.push('/')
}
</script>

<template>
  <div class="admin-login-page">
    <aside class="brand-panel">
      <div class="brand-deco brand-deco-1"></div>
      <div class="brand-deco brand-deco-2"></div>
      <div class="brand-deco brand-deco-3"></div>

      <div class="brand-inner">
        <div class="admin-badge">
          <el-icon class="badge-icon"><UserFilled /></el-icon>
          <span class="badge-text">管理后台</span>
        </div>

        <div class="brand-logo" @click="goHome">
          <span class="logo-icon">
            <el-icon><Setting /></el-icon>
          </span>
          <span class="logo-text">智学魔方</span>
        </div>

        <h1 class="brand-headline">智能管理平台<br />高效运营校园</h1>
        <p class="brand-desc">一站式管理系统，助力教育数字化转型</p>

        <ul class="brand-features">
          <li class="feature-item">
            <span class="feature-icon"><el-icon><UserFilled /></el-icon></span>
            <span class="feature-text">多角色权限管理</span>
          </li>
          <li class="feature-item">
            <span class="feature-icon"><el-icon><Setting /></el-icon></span>
            <span class="feature-text">灵活的系统配置</span>
          </li>
          <li class="feature-item">
            <span class="feature-icon"><el-icon><User /></el-icon></span>
            <span class="feature-text">用户全生命周期管理</span>
          </li>
        </ul>

        <div class="brand-stats">
          <div class="stat-item">
            <div class="stat-num">10万+</div>
            <div class="stat-label">服务学生</div>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <div class="stat-num">500+</div>
            <div class="stat-label">合作学校</div>
          </div>
        </div>
      </div>
    </aside>

    <main class="form-panel">
      <div class="form-card">
        <div class="form-header">
          <div class="admin-title-badge">
            <el-icon><UserFilled /></el-icon>
            <span>管理员登录</span>
          </div>
          <h2 class="form-title">欢迎回来</h2>
          <p class="form-subtitle">登录管理后台，管理智学魔方平台</p>
        </div>

        <form class="auth-form" @submit.prevent="handleLogin">
          <div class="form-group">
            <label class="form-label">管理员账号</label>
            <el-input
              v-model="loginForm.username"
              placeholder="请输入管理员账号"
              :prefix-icon="User"
              size="large"
              @keyup.enter="handleLogin"
              class="auth-input"
            />
          </div>

          <div class="form-group">
            <label class="form-label">密码</label>
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              :prefix-icon="Lock"
              size="large"
              show-password
              @keyup.enter="handleLogin"
              class="auth-input"
            />
          </div>

          <el-button
            type="primary"
            size="large"
            class="submit-btn"
            :loading="loading"
            @click="handleLogin"
          >
            <span>{{ loading ? '登录中...' : '登 录' }}</span>
            <el-icon v-if="!loading" class="btn-icon"><ArrowRight /></el-icon>
          </el-button>
        </form>

        <div class="form-footer">
          <span class="footer-text">返回首页？</span>
          <router-link to="/" class="switch-link">点击返回</router-link>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.admin-login-page {
  display: flex;
  min-height: 100vh;
  font-family: var(--font-body);
  background: var(--bg);
}

.brand-panel {
  flex: 0 0 480px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  padding: 48px;
  background: linear-gradient(135deg, #1E1B4B 0%, var(--primary-dark) 45%, var(--primary) 100%);
}

.brand-deco {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.08);
}

.brand-deco-1 {
  width: 320px;
  height: 320px;
  top: -100px;
  right: -120px;
}

.brand-deco-2 {
  width: 200px;
  height: 200px;
  bottom: -60px;
  left: -80px;
  background: rgba(255, 255, 255, 0.06);
}

.brand-deco-3 {
  width: 120px;
  height: 120px;
  top: 55%;
  right: 18%;
  background: rgba(255, 255, 255, 0.05);
}

.brand-inner {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 360px;
  color: var(--text-white);
}

.admin-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 6px 14px;
  background: rgba(255, 255, 255, 0.15);
  border: 1px solid rgba(255, 255, 255, 0.25);
  border-radius: var(--radius-full);
  font-size: 13px;
  font-weight: 600;
  margin-bottom: 32px;
  backdrop-filter: blur(10px);
}

.badge-icon {
  font-size: 14px;
}

.brand-logo {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 40px;
  cursor: pointer;
}

.logo-icon {
  width: 44px;
  height: 44px;
  background: rgba(255, 255, 255, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  backdrop-filter: blur(10px);
}

.logo-text {
  font-family: var(--font-heading);
  font-size: 22px;
  font-weight: 700;
  letter-spacing: 1px;
}

.brand-headline {
  font-family: var(--font-heading);
  font-size: 34px;
  font-weight: 800;
  line-height: 1.3;
  margin-bottom: 16px;
  letter-spacing: 0.5px;
}

.brand-desc {
  font-size: 15px;
  line-height: 1.7;
  color: rgba(255, 255, 255, 0.82);
  margin-bottom: 40px;
}

.brand-features {
  list-style: none;
  padding: 0;
  margin: 0 0 48px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.feature-icon {
  width: 24px;
  height: 24px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: var(--radius-full);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  flex-shrink: 0;
  color: var(--text-white);
}

.feature-text {
  font-size: 15px;
  font-weight: 500;
  color: rgba(255, 255, 255, 0.92);
}

.brand-stats {
  display: flex;
  align-items: center;
  gap: 24px;
  padding-top: 32px;
  border-top: 1px solid rgba(255, 255, 255, 0.15);
}

.stat-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-num {
  font-family: var(--font-heading);
  font-size: 24px;
  font-weight: 800;
  color: var(--text-white);
}

.stat-label {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.72);
}

.stat-divider {
  width: 1px;
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
}

.form-panel {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg);
  padding: 48px;
}

.form-card {
  width: 100%;
  max-width: 420px;
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 48px 40px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
}

.form-header {
  margin-bottom: 32px;
}

.admin-title-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 12px;
  background: var(--primary-50);
  color: var(--primary);
  border-radius: var(--radius-sm);
  font-size: 13px;
  font-weight: 600;
  margin-bottom: 16px;
}

.form-title {
  font-family: var(--font-heading);
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.form-subtitle {
  font-size: 15px;
  color: var(--text-secondary);
  line-height: 1.5;
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-label {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
}

.auth-input :deep(.el-input__wrapper) {
  border-radius: var(--radius-sm) !important;
  box-shadow: none !important;
  border: 1.5px solid var(--border) !important;
  padding: 6px 14px !important;
  transition: all var(--transition-normal) !important;
  background: var(--bg-card) !important;
}

.auth-input :deep(.el-input__wrapper:hover) {
  border-color: var(--primary-light) !important;
}

.auth-input :deep(.el-input__wrapper.is-focus) {
  border-color: var(--primary) !important;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.12) !important;
}

.auth-input :deep(.el-input__prefix .el-icon) {
  color: var(--text-muted);
}

.auth-input :deep(.el-input__inner) {
  font-size: 15px;
  color: var(--text-primary);
}

.submit-btn {
  width: 100%;
  height: 48px !important;
  border-radius: var(--radius-md) !important;
  font-size: 16px !important;
  font-weight: 700 !important;
  font-family: var(--font-body) !important;
  background: var(--primary) !important;
  border: none !important;
  box-shadow: var(--shadow-primary) !important;
  cursor: pointer;
  transition: all var(--transition-normal) !important;
  margin-top: 8px;
}

.submit-btn:hover {
  background: var(--primary-dark) !important;
  box-shadow: var(--shadow-primary-lg) !important;
  transform: translateY(-1px);
}

.submit-btn:active {
  transform: translateY(0);
}

.btn-icon {
  font-size: 16px;
  margin-left: 6px;
}

.form-footer {
  text-align: center;
  font-size: 14px;
  color: var(--text-secondary);
  margin-top: 28px;
}

.footer-text {
  color: var(--text-secondary);
}

.switch-link {
  color: var(--primary);
  font-weight: 600;
  margin-left: 4px;
  cursor: pointer;
  text-decoration: none;
  transition: color var(--transition-fast);
}

.switch-link:hover {
  color: var(--primary-dark);
}

@media (max-width: 960px) {
  .admin-login-page {
    flex-direction: column;
  }

  .brand-panel {
    flex: 0 0 auto;
    min-height: 280px;
    padding: 40px 32px;
  }

  .brand-headline {
    font-size: 26px;
  }

  .brand-features,
  .brand-stats {
    display: none;
  }

  .brand-logo {
    margin-bottom: 24px;
  }
}

@media (max-width: 600px) {
  .form-panel {
    padding: 32px 20px;
  }

  .form-card {
    padding: 32px 24px;
    box-shadow: none;
    border: none;
    background: transparent;
  }

  .form-title {
    font-size: 24px;
  }

  .brand-panel {
    min-height: 200px;
    padding: 32px 24px;
  }

  .brand-headline {
    font-size: 22px;
    margin-bottom: 12px;
  }

  .brand-desc {
    font-size: 14px;
    margin-bottom: 0;
  }
}

@media (prefers-reduced-motion: reduce) {
  .submit-btn,
  .switch-link {
    transition: none !important;
  }
}
</style>
