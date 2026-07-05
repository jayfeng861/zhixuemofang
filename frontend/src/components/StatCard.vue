<script setup lang="ts">
defineProps<{
  /** 统计标题 */
  title: string
  /** 统计数值 */
  value: string | number
  /** 图标类型标识，决定显示的 SVG 图标 */
  icon: string
  /** 图标/强调色 (hex) */
  color: string
  /** 数值后缀，如 '分钟', '道' */
  suffix?: string
  /** 趋势，如 '+12%' / '-3%' */
  trend?: string
}>()
</script>

<template>
  <div class="stat-card clay-card">
    <!-- 图标区域 -->
    <div class="stat-card__icon" :style="{ background: color + '14', color: color }">
      <!-- 书本图标 -->
      <svg v-if="icon == 'book'" width="24" height="24" viewBox="0 0 24 24"
        fill="none" stroke="currentColor" stroke-width="2"
        stroke-linecap="round" stroke-linejoin="round">
        <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z" />
        <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z" />
      </svg>

      <!-- 时钟图标 -->
      <svg v-else-if="icon == 'clock'" width="24" height="24" viewBox="0 0 24 24"
        fill="none" stroke="currentColor" stroke-width="2"
        stroke-linecap="round" stroke-linejoin="round">
        <circle cx="12" cy="12" r="10" />
        <polyline points="12 6 12 12 16 14" />
      </svg>

      <!-- 勾选图标 -->
      <svg v-else-if="icon == 'check'" width="24" height="24" viewBox="0 0 24 24"
        fill="none" stroke="currentColor" stroke-width="2"
        stroke-linecap="round" stroke-linejoin="round">
        <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" />
        <polyline points="22 4 12 14.01 9 11.01" />
      </svg>

      <!-- 火焰图标 (连续学习天数) -->
      <svg v-else-if="icon == 'fire'" width="24" height="24" viewBox="0 0 24 24"
        fill="none" stroke="currentColor" stroke-width="2"
        stroke-linecap="round" stroke-linejoin="round">
        <path d="M8.5 14.5A2.5 2.5 0 0 0 11 12c0-1.38-.5-2-1-3-1.072-2.143-.224-4.054 2-6 .5 2.5 2 4.9 4 6.5 2 1.6 3 3.5 3 5.5a7 7 0 1 1-14 0c0-1.153.433-2.294 1-3a2.5 2.5 0 0 0 2.5 2.5z" />
      </svg>

      <!-- 排名图标 -->
      <svg v-else-if="icon == 'rank'" width="24" height="24" viewBox="0 0 24 24"
        fill="none" stroke="currentColor" stroke-width="2"
        stroke-linecap="round" stroke-linejoin="round">
        <polyline points="8 21 8 14 4 14 4 10 8 10 8 3" />
        <polyline points="16 21 16 14 20 14 20 10 16 10 16 3" />
        <line x1="2" y1="21" x2="22" y2="21" />
      </svg>

      <!-- 默认圆点图标 -->
      <svg v-else width="24" height="24" viewBox="0 0 24 24"
        fill="none" stroke="currentColor" stroke-width="2"
        stroke-linecap="round" stroke-linejoin="round">
        <circle cx="12" cy="12" r="10" />
      </svg>
    </div>

    <!-- 信息区域 -->
    <div class="stat-card__info">
      <p class="stat-card__title">{{ title }}</p>
      <div class="stat-card__value">
        <span class="stat-card__number">{{ value }}</span>
        <span v-if="suffix" class="stat-card__suffix">{{ suffix }}</span>
        <!-- 趋势指示 -->
        <span
          v-if="trend"
          class="stat-card__trend"
          :class="trend.startsWith('+') || trend.startsWith('up') ? 'stat-card__trend--up' : 'stat-card__trend--down'"
        >
          <!-- 上升箭头 -->
          <svg v-if="trend.startsWith('+') || trend.startsWith('up')" width="12" height="12"
            viewBox="0 0 24 24" fill="none" stroke="currentColor"
            stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="18 15 12 9 6 15" />
          </svg>
          <!-- 下降箭头 -->
          <svg v-else width="12" height="12"
            viewBox="0 0 24 24" fill="none" stroke="currentColor"
            stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="6 9 12 15 18 9" />
          </svg>
          <span class="stat-card__trend-text">{{ trend.replace(/^(up|down)/, '') }}</span>
        </span>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* ---------- 统计卡片 ---------- */
.stat-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: var(--space-lg);
  display: flex;
  align-items: center;
  gap: var(--space-md);
  box-shadow: var(--shadow-md);
  transition: transform var(--transition-normal), box-shadow var(--transition-normal);
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

/* ---------- 图标 ---------- */
.stat-card__icon {
  width: 52px;
  height: 52px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

/* ---------- 信息 ---------- */
.stat-card__info {
  flex: 1;
  min-width: 0;
}

.stat-card__title {
  font-size: 13px;
  color: var(--text-secondary);
  margin-bottom: 4px;
  font-weight: 500;
}

.stat-card__value {
  display: flex;
  align-items: baseline;
  gap: var(--space-xs);
}

/* ---------- 大号数字 ---------- */
.stat-card__number {
  font-size: 2rem;
  font-weight: 700;
  color: var(--primary);
  line-height: 1.2;
  letter-spacing: -0.02em;
}

/* ---------- 后缀 ---------- */
.stat-card__suffix {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
}

/* ---------- 趋势 ---------- */
.stat-card__trend {
  display: inline-flex;
  align-items: center;
  gap: 2px;
  font-size: 12px;
  font-weight: 600;
  margin-left: var(--space-xs);
}

.stat-card__trend--up {
  color: var(--success);
}

.stat-card__trend--down {
  color: var(--danger);
}
</style>
