<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/store'
import AppHeader from '@/components/AppHeader.vue'
import AppSidebar from '@/components/AppSidebar.vue'

const route = useRoute()
const userStore = useUserStore()

/** 登录 / 注册页面使用全屏布局，不显示导航栏和侧边栏 */
const isAuthPage = computed(() => {
  const path = route.path
  return path == '/login' || path == '/register'
})

/** 已登录且非认证页面时展示完整布局 */
const showLayout = computed(() => userStore.isLoggedIn && !isAuthPage.value)

/** 侧边栏折叠宽度，与 CSS 变量保持同步 */
const sidebarWidth = computed(() => {
  // 由 AppSidebar 通过 provide/inject 传出; 这里用固定值即可，主内容区通过 CSS margin-left 过渡
})
</script>

<template>
  <div class="app-root" :class="{ 'app-root--auth': isAuthPage }">
    <!-- == 完整布局 == -->
    <template v-if="showLayout">
      <AppHeader />
      <div class="app-body">
        <AppSidebar />
        <main class="app-main">
          <router-view v-slot="{ Component, route: viewRoute }">
            <transition name="page-fade-up" mode="out-in">
              <component :is="Component" :key="viewRoute.path" />
            </transition>
          </router-view>
        </main>
      </div>
    </template>

    <!-- == 全屏布局 (登录/注册) == -->
    <template v-else>
      <router-view v-slot="{ Component, route: viewRoute }">
        <transition name="page-fade-up" mode="out-in">
          <component :is="Component" :key="viewRoute.path" />
        </transition>
      </router-view>
    </template>
  </div>
</template>

<style scoped>
/* ---------- 根容器 ---------- */
.app-root {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--bg);
  font-family: var(--font-body);
  color: var(--text-primary);
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.app-root--auth {
  background: linear-gradient(135deg, #EEF2FF 0%, #E0E7FF 50%, #F5F3FF 100%);
}

/* ---------- 主体区域 ---------- */
.app-body {
  display: flex;
  flex: 1;
  padding-top: calc(var(--header-height) + var(--space-lg));
}

/* ---------- 主内容区 ---------- */
.app-main {
  flex: 1;
  margin-left: var(--sidebar-expanded);
  padding: var(--space-lg) var(--space-2xl);
  min-height: calc(100vh - var(--header-height));
  transition: margin-left var(--transition-normal);
  max-width: calc(100% - var(--sidebar-expanded));
}

/* ---------- 页面切换过渡 (fade + slideUp) ---------- */
.page-fade-up-enter-active {
  transition: opacity 0.25s ease, transform 0.25s ease;
}

.page-fade-up-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.page-fade-up-enter-from {
  opacity: 0;
  transform: translateY(12px);
}

.page-fade-up-leave-to {
  opacity: 0;
  transform: translateY(-8px);
}

.page-fade-up-enter-to,
.page-fade-up-leave-from {
  opacity: 1;
  transform: translateY(0);
}
</style>
