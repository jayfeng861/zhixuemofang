<script setup lang="ts">
defineProps<{
  title: string
  /** 图标颜色 (hex) */
  iconColor: string
  /** 渐变色背景，如 var(--chinese-gradient) */
  gradient: string
  description: string
  /** 可选统计数据，如 '已掌握 32/50' */
  stats?: string
}>()

defineEmits<{
  click: []
}>()
</script>

<template>
  <div class="subject-card clay-card" @click="$emit('click')">
    <!-- 渐变背景层 -->
    <div class="subject-card__bg" :style="{ background: gradient }"></div>

    <!-- 内容 -->
    <div class="subject-card__content">
      <!-- 左侧图标区域 -->
      <div class="subject-card__icon-wrap">
        <svg class="subject-card__icon" width="36" height="36" viewBox="0 0 24 24"
          fill="none" :stroke="iconColor" stroke-width="2"
          stroke-linecap="round" stroke-linejoin="round">
          <!-- 默认书本图标 -->
          <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z" />
          <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z" />
        </svg>
      </div>

      <!-- 右侧信息区域 -->
      <div class="subject-card__info">
        <h3 class="subject-card__title">{{ title }}</h3>
        <p class="subject-card__desc">{{ description }}</p>
        <span v-if="stats" class="subject-card__stats">{{ stats }}</span>
      </div>
    </div>

    <!-- 装饰圆 -->
    <div class="subject-card__deco"></div>
  </div>
</template>

<style scoped>
/* ---------- 学科卡片 ---------- */
.subject-card {
  position: relative;
  border-radius: var(--radius-lg);
  padding: var(--space-lg);
  cursor: pointer;
  overflow: hidden;
  min-height: 130px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  transition: transform var(--transition-normal), box-shadow var(--transition-normal);
}

.subject-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-xl);
}

/* ---------- 渐变背景 ---------- */
.subject-card__bg {
  position: absolute;
  inset: 0;
  opacity: 0.92;
  border-radius: inherit;
}

/* ---------- 内容层 ---------- */
.subject-card__content {
  position: relative;
  z-index: 1;
  display: flex;
  align-items: center;
  gap: var(--space-md);
}

/* ---------- 图标区域 ---------- */
.subject-card__icon-wrap {
  width: 56px;
  height: 56px;
  border-radius: var(--radius-md);
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(8px);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.subject-card__icon {
  filter: drop-shadow(0 1px 2px rgba(0, 0, 0, 0.12));
}

/* ---------- 信息区域 ---------- */
.subject-card__info {
  flex: 1;
  min-width: 0;
}

.subject-card__title {
  font-family: var(--font-heading);
  font-size: 18px;
  font-weight: 700;
  color: var(--text-white);
  margin-bottom: 4px;
  line-height: 1.3;
}

.subject-card__desc {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.85);
  line-height: 1.5;
  margin-bottom: var(--space-xs);
}

.subject-card__stats {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.95);
  padding: 3px 10px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: var(--radius-full);
}

/* ---------- 装饰 ---------- */
.subject-card__deco {
  position: absolute;
  right: -24px;
  bottom: -24px;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.08);
  pointer-events: none;
}
</style>
