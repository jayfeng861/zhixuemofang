<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { RouterLink } from 'vue-router'

const route = useRoute()
const collapsed = ref(false)

/** 当前激活路径 */
const activePath = computed(() => route.path)

/** 菜单分组定义（覆盖全部22个路由） */
const menuGroups = [
  {
    group: '首页',
    items: [
      {
        label: '首页总览',
        path: '/',
        icon: 'home',
      },
    ],
  },
  {
    group: '语文模块',
    items: [
      {
        label: '文言文剧场',
        path: '/chinese/wenyanwen',
        icon: 'wenyanwen',
      },
      {
        label: '作文写作',
        path: '/chinese/essay/write',
        icon: 'essay-write',
      },
      {
        label: '我的作文',
        path: '/chinese/essay/list',
        icon: 'essay-list',
      },
    ],
  },
  {
    group: '数学模块',
    items: [
      {
        label: '错题本',
        path: '/math/wrong-questions',
        icon: 'wrong-book',
      },
      {
        label: '错题溯源分析',
        path: '/math/wrong-analysis',
        icon: 'wrong-analysis',
      },
      {
        label: '动态几何实验室',
        path: '/math/geometry',
        icon: 'geometry',
      },
    ],
  },
  {
    group: '英语模块',
    items: [
      {
        label: '词汇记忆',
        path: '/english/vocabulary',
        icon: 'vocabulary',
      },
      {
        label: '词汇测试',
        path: '/english/vocabulary/test',
        icon: 'vocab-test',
      },
      {
        label: 'AI口语对练舱',
        path: '/english/oral',
        icon: 'oral',
      },
      {
        label: '口语练习历史',
        path: '/english/oral/history',
        icon: 'oral-history',
      },
    ],
  },
  {
    group: '社区',
    items: [
      {
        label: '学习社区',
        path: '/community/posts',
        icon: 'community',
      },
      {
        label: '发表帖子',
        path: '/community/create-post',
        icon: 'create-post',
      },
    ],
  },
  {
    group: '数据中心',
    items: [
      {
        label: '学习数据看板',
        path: '/dashboard/overview',
        icon: 'dashboard',
      },
      {
        label: '排行榜',
        path: '/dashboard/ranking',
        icon: 'ranking',
      },
    ],
  },
  {
    group: '个人中心',
    items: [
      {
        label: '个人中心',
        path: '/profile',
        icon: 'profile',
      },
    ],
  },
]

/** 判断菜单项是否激活（支持子路径匹配） */
function isActive(path: string): boolean {
  if (path == '/') return activePath.value == '/'
  return activePath.value.startsWith(path)
}
</script>

<template>
  <aside class="app-sidebar" :class="{ 'app-sidebar--collapsed': collapsed }">
    <!-- == 菜单列表 == -->
    <nav class="sidebar-nav">
      <div
        v-for="group in menuGroups"
        :key="group.group"
        class="menu-group"
      >
        <div class="menu-group__title">
          <span class="menu-group__label">{{ group.group }}</span>
        </div>

        <RouterLink
          v-for="item in group.items"
          :key="item.path"
          :to="item.path"
          class="menu-item"
          :class="{ 'menu-item--active': isActive(item.path) }"
          :title="collapsed ? item.label : undefined"
        >
          <!-- 图标区域 -->
          <span class="menu-item__icon">
            <!-- 首页 -->
            <svg v-if="item.icon == 'home'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
              <polyline points="9 22 9 12 15 12 15 22" />
            </svg>

            <!-- 文言文 -->
            <svg v-else-if="item.icon == 'wenyanwen'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20" />
              <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z" />
              <line x1="9" y1="7" x2="15" y2="7" />
              <line x1="9" y1="11" x2="15" y2="11" />
            </svg>

            <!-- 作文写作 -->
            <svg v-else-if="item.icon == 'essay-write'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 20h9" />
              <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z" />
            </svg>

            <!-- 作文列表 -->
            <svg v-else-if="item.icon == 'essay-list'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z" />
              <polyline points="14 2 14 8 20 8" />
              <line x1="9" y1="13" x2="15" y2="13" />
              <line x1="9" y1="17" x2="15" y2="17" />
            </svg>

            <!-- 错题本 -->
            <svg v-else-if="item.icon == 'wrong-book'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10" />
              <line x1="12" y1="8" x2="12" y2="12" />
              <line x1="12" y1="16" x2="12.01" y2="16" />
            </svg>

            <!-- 错题溯源分析 -->
            <svg v-else-if="item.icon == 'wrong-analysis'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <line x1="18" y1="20" x2="18" y2="10" />
              <line x1="12" y1="20" x2="12" y2="4" />
              <line x1="6" y1="20" x2="6" y2="14" />
              <polyline points="10 6 12 4 14 6" />
            </svg>

            <!-- 几何实验室 -->
            <svg v-else-if="item.icon == 'geometry'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polygon points="12 2 22 8.5 22 15.5 12 22 2 15.5 2 8.5 12 2" />
              <line x1="12" y1="22" x2="12" y2="15.5" />
              <polyline points="22 8.5 12 15.5 2 8.5" />
            </svg>

            <!-- 词汇记忆 -->
            <svg v-else-if="item.icon == 'vocabulary'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z" />
              <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z" />
            </svg>

            <!-- 词汇测试 -->
            <svg v-else-if="item.icon == 'vocab-test'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M9 11l3 3L22 4" />
              <path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11" />
            </svg>

            <!-- AI口语对练舱 -->
            <svg v-else-if="item.icon == 'oral'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 1a3 3 0 0 0-3 3v8a3 3 0 0 0 6 0V4a3 3 0 0 0-3-3z" />
              <path d="M19 10v2a7 7 0 0 1-14 0v-2" />
              <line x1="12" y1="19" x2="12" y2="23" />
              <line x1="8" y1="23" x2="16" y2="23" />
            </svg>

            <!-- 口语历史 -->
            <svg v-else-if="item.icon == 'oral-history'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10" />
              <polyline points="12 6 12 12 16 14" />
            </svg>

            <!-- 学习社区 -->
            <svg v-else-if="item.icon == 'community'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
              <circle cx="9" cy="7" r="4" />
              <path d="M23 21v-2a4 4 0 0 0-3-3.87" />
              <path d="M16 3.13a4 4 0 0 1 0 7.75" />
            </svg>

            <!-- 发表帖子 -->
            <svg v-else-if="item.icon == 'create-post'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <line x1="12" y1="5" x2="12" y2="19" />
              <line x1="5" y1="12" x2="19" y2="12" />
            </svg>

            <!-- 数据看板 -->
            <svg v-else-if="item.icon == 'dashboard'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <rect x="3" y="3" width="7" height="7" rx="1" />
              <rect x="14" y="3" width="7" height="7" rx="1" />
              <rect x="3" y="14" width="7" height="7" rx="1" />
              <rect x="14" y="14" width="7" height="7" rx="1" />
            </svg>

            <!-- 排行榜 -->
            <svg v-else-if="item.icon == 'ranking'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M8 21h8" />
              <path d="M12 17v4" />
              <path d="M7 4h10l-2 8H9L7 4z" />
              <path d="M5 12l2-8" />
              <path d="M19 12l-2-8" />
            </svg>

            <!-- 个人中心 -->
            <svg v-else-if="item.icon == 'profile'" width="20" height="20" viewBox="0 0 24 24"
              fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
              <circle cx="12" cy="7" r="4" />
            </svg>
          </span>

          <!-- 文字标签 -->
          <span v-if="!collapsed" class="menu-item__label">{{ item.label }}</span>
        </RouterLink>
      </div>
    </nav>

    <!-- == 折叠切换按钮 == -->
    <button class="sidebar-toggle" @click="collapsed = !collapsed">
      <svg
        width="18" height="18" viewBox="0 0 24 24"
        fill="none" stroke="currentColor" stroke-width="2"
        stroke-linecap="round" stroke-linejoin="round"
        :style="{ transform: collapsed ? 'rotate(180deg)' : 'rotate(0deg)' }"
      >
        <polyline points="15 18 9 12 15 6" />
      </svg>
    </button>
  </aside>
</template>

<style scoped>
/* ---------- 侧边栏容器 ---------- */
.app-sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: var(--sidebar-expanded);
  height: 100vh;
  background: rgba(255, 255, 255, 0.65);
  backdrop-filter: blur(16px) saturate(1.3);
  -webkit-backdrop-filter: blur(16px) saturate(1.3);
  border-right: 1px solid var(--border);
  display: flex;
  flex-direction: column;
  z-index: 900;
  padding: calc(var(--header-height) + var(--space-sm)) 0 var(--space-lg);
  overflow: hidden;
  transition: width var(--transition-normal);
}

.app-sidebar--collapsed {
  width: var(--sidebar-collapsed);
}

/* ---------- 菜单导航 ---------- */
.sidebar-nav {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 0 var(--space-sm);
}

/* ---------- 菜单分组 ---------- */
.menu-group {
  margin-bottom: var(--space-sm);
}

.menu-group__title {
  padding: var(--space-sm) var(--space-md) var(--space-xs);
}

.menu-group__label {
  font-size: 11px;
  font-weight: 700;
  color: var(--text-muted);
  text-transform: uppercase;
  letter-spacing: 0.08em;
  white-space: nowrap;
  overflow: hidden;
  transition: all var(--transition-fast);
}

/* 折叠态：文字隐藏，用分割线代替 */
.app-sidebar--collapsed .menu-group__label {
  font-size: 0;
  opacity: 0;
}

.app-sidebar--collapsed .menu-group__title {
  padding: var(--space-xs) var(--space-sm);
}

.app-sidebar--collapsed .menu-group__title::after {
  content: '';
  display: block;
  width: 24px;
  height: 2px;
  background: var(--border);
  border-radius: 1px;
  margin: 4px auto 0;
}

/* ---------- 菜单项 ---------- */
.menu-item {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  padding: 10px var(--space-md);
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
  text-decoration: none;
  white-space: nowrap;
  cursor: pointer;
  transition: all var(--transition-fast);
  margin-bottom: 2px;
  position: relative;
}

.menu-item:hover {
  color: var(--primary);
  background: var(--primary-50);
}

.menu-item--active {
  color: var(--primary);
  background: var(--primary-100);
  font-weight: 600;
}

.menu-item--active::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 20px;
  background: var(--primary);
  border-radius: 0 2px 2px 0;
}

/* 折叠态：居中图标 */
.app-sidebar--collapsed .menu-item {
  justify-content: center;
  padding: 10px;
}

.app-sidebar--collapsed .menu-item--active::before {
  left: -4px;
}

/* ---------- 图标 ---------- */
.menu-item__icon {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  width: 20px;
  height: 20px;
}

.menu-item__icon svg {
  transition: transform var(--transition-fast);
}

.menu-item:hover .menu-item__icon svg {
  transform: scale(1.08);
}

/* ---------- 文字标签 ---------- */
.menu-item__label {
  overflow: hidden;
  text-overflow: ellipsis;
  transition: opacity var(--transition-fast);
}

.app-sidebar--collapsed .menu-item__label {
  display: none;
}

/* ---------- 折叠按钮 ---------- */
.sidebar-toggle {
  position: absolute;
  bottom: var(--space-md);
  left: 50%;
  transform: translateX(-50%);
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid var(--border);
  border-radius: 50%;
  background: var(--bg-card);
  color: var(--text-muted);
  cursor: pointer;
  box-shadow: var(--shadow-sm);
  transition: all var(--transition-fast);
}

.sidebar-toggle:hover {
  color: var(--primary);
  border-color: var(--primary-light);
  background: var(--bg-hover);
  box-shadow: var(--shadow-md);
}

.sidebar-toggle svg {
  transition: transform var(--transition-normal);
}

/* ---------- 滚动条 ---------- */
.sidebar-nav::-webkit-scrollbar {
  width: 4px;
}

.sidebar-nav::-webkit-scrollbar-track {
  background: transparent;
}

.sidebar-nav::-webkit-scrollbar-thumb {
  background: var(--border);
  border-radius: 2px;
}

.sidebar-nav::-webkit-scrollbar-thumb:hover {
  background: var(--text-muted);
}
</style>
