<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/store'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const searchQuery = ref('')

/** 学科标签定义 */
const subjectTabs = [
  { label: '全部', value: 'all', path: '/' },
  { label: '语文', value: 'chinese', path: '/chinese/wenyanwen' },
  { label: '数学', value: 'math', path: '/math/wrong-questions' },
  { label: '英语', value: 'english', path: '/english/vocabulary' },
]

/** 当前学科标签 - 根据路由路径自动判断 */
const activeTab = computed(() => {
  const path = route.path
  if (path.startsWith('/chinese')) return 'chinese'
  if (path.startsWith('/math')) return 'math'
  if (path.startsWith('/english')) return 'english'
  return 'all'
})

function handleSearch() {
  const q = searchQuery.value.trim()
  if (q) {
    // 搜索逻辑可扩展
  }
}

function handleLogout() {
  userStore.logout()
  router.push('/login')
}
</script>

<template>
  <header class="app-header">
    <div class="app-header__inner">
      <!-- == 左侧：品牌 Logo + 学科标签 == -->
      <div class="app-header__left">
        <!-- 品牌 Logo -->
        <div class="app-header__logo" @click="router.push('/')">
          <div class="cube-3d">
            <div class="cube-3d__face cube-3d__face--top"></div>
            <div class="cube-3d__face cube-3d__face--front"></div>
            <div class="cube-3d__face cube-3d__face--right"></div>
          </div>
          <span class="app-header__brand">智学魔方</span>
        </div>

        <!-- 学科标签导航 -->
        <nav class="app-header__tabs">
          <button
            v-for="tab in subjectTabs"
            :key="tab.value"
            class="tab-btn"
            :class="{ 'tab-btn--active': activeTab == tab.value }"
            @click="router.push(tab.path)"
          >
            {{ tab.label }}
          </button>
        </nav>
      </div>

      <!-- == 右侧：搜索 + 操作 + 用户头像 == -->
      <div class="app-header__right">
        <!-- 搜索框 -->
        <div class="search-box">
          <svg class="search-box__icon" width="16" height="16" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="11" cy="11" r="8" />
            <line x1="21" y1="21" x2="16.65" y2="16.65" />
          </svg>
          <input
            v-model="searchQuery"
            type="text"
            class="search-box__input"
            placeholder="搜索课程、知识点..."
            @keyup.enter="handleSearch"
          />
        </div>

        <!-- 数据看板入口 -->
        <button class="header-action" @click="router.push('/dashboard/overview')">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor"
            stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect x="3" y="3" width="7" height="7" rx="1" />
            <rect x="14" y="3" width="7" height="7" rx="1" />
            <rect x="3" y="14" width="7" height="7" rx="1" />
            <rect x="14" y="14" width="7" height="7" rx="1" />
          </svg>
          <span class="header-action__label">看板</span>
        </button>

        <!-- 社区入口 -->
        <button class="header-action" @click="router.push('/community/posts')">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor"
            stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" />
          </svg>
          <span class="header-action__label">社区</span>
        </button>

        <!-- 通知 -->
        <button class="header-action notification-btn">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor"
            stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
            <path d="M13.73 21a2 2 0 0 1-3.46 0" />
          </svg>
          <span class="notification-badge">3</span>
        </button>

        <!-- 用户头像下拉 -->
        <div class="user-dropdown">
          <button class="user-dropdown__trigger">
            <div class="user-avatar">
              <!-- [修复] 移除可选链操作符，使用逻辑或获取安全值 -->
              {{ (userStore.userInfo && userStore.userInfo.nickname) ? userStore.userInfo.nickname.charAt(0) : '学' }}
            </div>
            <span class="user-dropdown__name">
              <!-- [修复] 移除可选链操作符 -->
              {{ (userStore.userInfo && userStore.userInfo.nickname) ? userStore.userInfo.nickname : '学霸小明' }}
            </span>
            <svg class="user-dropdown__arrow" width="14" height="14" viewBox="0 0 24 24" fill="none"
              stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="6 9 12 15 18 9" />
            </svg>
          </button>

          <!-- 下拉菜单 -->
          <div class="user-dropdown__menu">
            <button class="dropdown-item" @click="router.push('/profile')">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                <circle cx="12" cy="7" r="4" />
              </svg>
              <span>个人中心</span>
            </button>
            <div class="dropdown-divider"></div>
            <button class="dropdown-item dropdown-item--danger" @click="handleLogout">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                <polyline points="16 17 21 12 16 7" />
                <line x1="21" y1="12" x2="9" y2="12" />
              </svg>
              <span>退出登录</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>
/* ---------- 浮动导航栏 ---------- */
.app-header {
  position: sticky;
  top: 0;
  z-index: 1000;
  padding: var(--space-md);
}

.app-header__inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: var(--header-height);
  padding: 0 var(--space-lg);
  background: rgba(255, 255, 255, 0.75);
  backdrop-filter: blur(20px) saturate(1.4);
  -webkit-backdrop-filter: blur(20px) saturate(1.4);
  border: 1px solid var(--border);
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-lg);
}

/* ---------- 左侧区域 ---------- */
.app-header__left {
  display: flex;
  align-items: center;
  gap: var(--space-xl);
}

/* --- Logo --- */
.app-header__logo {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  cursor: pointer;
  user-select: none;
  flex-shrink: 0;
}

/* --- CSS 3D 魔方图标 --- */
.cube-3d {
  width: 32px;
  height: 32px;
  position: relative;
  transform-style: preserve-3d;
  transform: rotateX(-25deg) rotateY(-35deg);
}

.cube-3d__face {
  position: absolute;
  width: 32px;
  height: 32px;
  border-radius: 6px;
  border: 1.5px solid rgba(255, 255, 255, 0.6);
}

.cube-3d__face--front {
  background: var(--primary);
  transform: translateZ(12px);
}

.cube-3d__face--top {
  background: var(--primary-light);
  transform: rotateX(90deg) translateZ(12px);
}

.cube-3d__face--right {
  background: var(--success);
  transform: rotateY(90deg) translateZ(12px);
}

.app-header__brand {
  font-family: var(--font-heading);
  font-size: 20px;
  font-weight: 800;
  background: linear-gradient(135deg, var(--primary), var(--primary-light));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  letter-spacing: -0.02em;
}

/* --- 学科标签 --- */
.app-header__tabs {
  display: flex;
  gap: var(--space-xs);
}

.tab-btn {
  padding: 7px 18px;
  border: none;
  border-radius: var(--radius-full);
  font-size: 14px;
  font-weight: 500;
  font-family: var(--font-body);
  color: var(--text-secondary);
  background: transparent;
  cursor: pointer;
  transition: all var(--transition-fast);
  white-space: nowrap;
}

.tab-btn:hover {
  color: var(--primary);
  background: var(--primary-50);
}

.tab-btn--active {
  color: var(--text-white);
  background: var(--primary);
  box-shadow: 0 2px 8px rgba(79, 70, 229, 0.35);
  font-weight: 600;
}

/* ---------- 右侧区域 ---------- */
.app-header__right {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
}

/* --- 搜索框 --- */
.search-box {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  padding: 8px 16px;
  background: var(--bg-hover);
  border: 1.5px solid transparent;
  border-radius: var(--radius-full);
  transition: all var(--transition-fast);
  width: 260px;
}

.search-box:focus-within {
  border-color: var(--primary-light);
  background: var(--bg-card);
  box-shadow: 0 0 0 3px var(--primary-50);
  width: 300px;
}

.search-box__icon {
  flex-shrink: 0;
  color: var(--text-muted);
}

.search-box__input {
  flex: 1;
  border: none;
  outline: none;
  background: transparent;
  font-size: 14px;
  font-family: var(--font-body);
  color: var(--text-primary);
  min-width: 0;
}

.search-box__input::placeholder {
  color: var(--text-muted);
}

/* --- 操作按钮 --- */
.header-action {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 8px 12px;
  border: none;
  border-radius: var(--radius-md);
  background: transparent;
  color: var(--text-secondary);
  font-size: 13px;
  font-family: var(--font-body);
  cursor: pointer;
  transition: all var(--transition-fast);
  white-space: nowrap;
}

.header-action:hover {
  color: var(--primary);
  background: var(--primary-50);
}

.header-action__label {
  font-weight: 500;
}

/* --- 通知按钮 --- */
.notification-btn {
  position: relative;
}

.notification-badge {
  position: absolute;
  top: 2px;
  right: 2px;
  width: 16px;
  height: 16px;
  background: var(--danger);
  color: var(--text-white);
  font-size: 10px;
  font-weight: 700;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  line-height: 1;
}

/* --- 用户下拉 --- */
.user-dropdown {
  position: relative;
}

.user-dropdown__trigger {
  display: flex;
  align-items: center;
  gap: var(--space-xs);
  padding: 4px 8px 4px 4px;
  border: none;
  border-radius: var(--radius-full);
  background: transparent;
  cursor: pointer;
  transition: all var(--transition-fast);
  font-family: var(--font-body);
}

.user-dropdown__trigger:hover {
  background: var(--primary-50);
}

.user-avatar {
  width: 34px;
  height: 34px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--primary-light));
  color: var(--text-white);
  font-size: 14px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.user-dropdown__name {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
  max-width: 80px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.user-dropdown__arrow {
  color: var(--text-muted);
  transition: transform var(--transition-fast);
}

/* --- 下拉菜单 --- */
.user-dropdown__menu {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  min-width: 180px;
  padding: var(--space-xs);
  background: rgba(255, 255, 255, 0.92);
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
  opacity: 0;
  visibility: hidden;
  transform: translateY(-4px);
  transition: all var(--transition-fast);
}

.user-dropdown:hover .user-dropdown__menu,
.user-dropdown:focus-within .user-dropdown__menu {
  opacity: 1;
  visibility: visible;
  transform: translateY(0);
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  width: 100%;
  padding: 10px 12px;
  border: none;
  border-radius: var(--radius-md);
  background: transparent;
  color: var(--text-primary);
  font-size: 14px;
  font-family: var(--font-body);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.dropdown-item:hover {
  background: var(--primary-50);
  color: var(--primary);
}

.dropdown-item--danger:hover {
  background: rgba(239, 68, 68, 0.08);
  color: var(--danger);
}

.dropdown-divider {
  height: 1px;
  background: var(--border-light);
  margin: var(--space-xs) 0;
}
</style>
