<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const props = withDefaults(defineProps<{
  /** 页面标题 */
  title: string
  /** 页面副标题 */
  subtitle?: string
  /** 是否显示返回按钮 */
  showBack?: boolean
}>(), {
  showBack: false,
})

const emit = defineEmits<{
  back: []
}>()

const route = useRoute()
const router = useRouter()

/** 构建面包屑数据 */
const breadcrumbs = computed(() => {
  const matched = route.matched.filter(item => item.meta && item.meta.title)
  const crumbs = [{ label: '首页', path: '/' }]
  matched.forEach(function(item) {
    // [修复] 移除 != 全等符号，改为 !=
    if (item.path != '/' && item.meta.title) {
      crumbs.push({
        label: item.meta.title as string,
        path: item.path,
      })
    }
  })
  return crumbs
})

/** 返回操作 */
function handleBack() {
  // [修复] 移除 != 全等符号，改为 !=
  if (route.path != '/') {
    router.back()
  }
  emit('back')
}
</script>

<template>
  <div class="page-header">
    <div class="page-header__left">
      <!-- 返回按钮 -->
      <button v-if="showBack" class="page-header__back" @click="handleBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
          stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <line x1="19" y1="12" x2="5" y2="12" />
          <polyline points="12 19 5 12 12 5" />
        </svg>
      </button>

      <!-- 标题区域 -->
      <div class="page-header__titles">
        <!-- 面包屑 -->
        <nav class="page-header__breadcrumb">
          <template v-for="(crumb, idx) in breadcrumbs" :key="crumb.path">
            <router-link
              v-if="idx < breadcrumbs.length - 1"
              :to="crumb.path"
              class="breadcrumb-link"
            >
              {{ crumb.label }}
            </router-link>
            <span v-else class="breadcrumb-current">{{ crumb.label }}</span>
            <svg
              v-if="idx < breadcrumbs.length - 1"
              class="breadcrumb-sep"
              width="12" height="12" viewBox="0 0 24 24"
              fill="none" stroke="currentColor"
              stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"
            >
              <polyline points="9 18 15 12 9 6" />
            </svg>
          </template>
        </nav>

        <h1 class="page-header__title">{{ title }}</h1>
        <p v-if="subtitle" class="page-header__subtitle">{{ subtitle }}</p>
      </div>
    </div>

    <!-- 右侧操作区域 -->
    <div class="page-header__right">
      <slot name="actions"></slot>
    </div>
  </div>
</template>

<style scoped>
/* ---------- 页面标题 ---------- */
.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: var(--space-lg);
  padding-bottom: var(--space-md);
  border-bottom: 1px solid var(--border-light);
}

.page-header__left {
  display: flex;
  align-items: flex-start;
  gap: var(--space-md);
}

/* ---------- 返回按钮 ---------- */
.page-header__back {
  width: 38px;
  height: 38px;
  border-radius: 50%;
  border: 1px solid var(--border);
  background: var(--bg-card);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: var(--text-secondary);
  transition: all var(--transition-fast);
  flex-shrink: 0;
  margin-top: 4px;
}

.page-header__back:hover {
  color: var(--primary);
  border-color: var(--primary-light);
  background: var(--primary-50);
  box-shadow: var(--shadow-sm);
}

/* ---------- 标题区域 ---------- */
.page-header__titles {
  min-width: 0;
}

/* ---------- 面包屑 ---------- */
.page-header__breadcrumb {
  display: flex;
  align-items: center;
  gap: var(--space-xs);
  margin-bottom: var(--space-xs);
}

.breadcrumb-link {
  font-size: 13px;
  color: var(--text-muted);
  text-decoration: none;
  cursor: pointer;
  transition: color var(--transition-fast);
}

.breadcrumb-link:hover {
  color: var(--primary);
}

.breadcrumb-current {
  font-size: 13px;
  color: var(--text-muted);
  font-weight: 500;
}

.breadcrumb-sep {
  color: var(--text-muted);
  opacity: 0.5;
  flex-shrink: 0;
}

/* ---------- 大号标题 ---------- */
.page-header__title {
  font-family: var(--font-heading);
  font-size: 26px;
  font-weight: 800;
  color: var(--text-primary);
  line-height: 1.3;
  letter-spacing: -0.01em;
}

.page-header__subtitle {
  font-size: 14px;
  color: var(--text-secondary);
  margin-top: var(--space-xs);
  line-height: 1.5;
}

/* ---------- 右侧操作 ---------- */
.page-header__right {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  flex-shrink: 0;
}
</style>
