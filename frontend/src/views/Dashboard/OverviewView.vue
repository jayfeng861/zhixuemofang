<script setup lang="ts">
import { ref } from 'vue'
import PageHeader from '@/components/PageHeader.vue'
import ChartPanel from '@/components/ChartPanel.vue'
import {
  Clock,
  CircleCheck,
  Calendar,
  Trophy,
  TrendCharts,
  Aim,
  ArrowUp,
  ArrowRight,
  Notebook,
  ChatLineRound,
  Reading,
  Collection
} from '@element-plus/icons-vue'

const weekDays = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const lineOption = ref<any>({
  tooltip: { trigger: 'axis' },
  legend: { data: ['学习时长(分钟)', '正确率(%)'] },
  grid: { left: 50, right: 50, top: 50, bottom: 30 },
  xAxis: { type: 'category', data: weekDays },
  yAxis: [{ type: 'value', name: '时长' }, { type: 'value', name: '正确率', max: 100 }],
  series: [
    { name: '学习时长(分钟)', type: 'line', smooth: true, data: [90, 120, 80, 150, 110, 130, 127], itemStyle: { color: '#4F46E5' }, areaStyle: { color: 'rgba(79, 70, 229, 0.12)' }, lineStyle: { width: 3 } },
    { name: '正确率(%)', type: 'line', smooth: true, yAxisIndex: 1, data: [85, 78, 92, 88, 75, 90, 82], itemStyle: { color: '#22C55E' }, lineStyle: { width: 3 } }
  ]
})

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const barOption = ref<any>({
  tooltip: { trigger: 'axis' },
  grid: { left: 50, right: 30, top: 30, bottom: 30 },
  xAxis: { type: 'category', data: ['语文', '数学', '英语'] },
  yAxis: { type: 'value', name: '正确率(%)', max: 100 },
  series: [{
    type: 'bar',
    data: [
      { value: 85, itemStyle: { color: '#F97316' } },
      { value: 72, itemStyle: { color: '#7C3AED' } },
      { value: 90, itemStyle: { color: '#06B6D4' } }
    ],
    barWidth: 40,
    borderRadius: [8, 8, 0, 0]
  }]
})

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const heatmapOption = ref<any>({
  tooltip: { formatter: (p: any) => `${p.data[0]}-${p.data[1]} 掌握度: ${p.data[2]}%` },
  grid: { left: 60, right: 30, top: 30, bottom: 30 },
  xAxis: { type: 'category', data: weekDays, splitArea: { show: true } },
  yAxis: { type: 'category', data: ['语文', '数学', '英语'], splitArea: { show: true } },
  visualMap: { min: 0, max: 100, calculable: true, orient: 'horizontal', left: 'center', bottom: -10, inRange: { color: ['#F1F5F9', '#4F46E5'] } },
  series: [{
    type: 'heatmap',
    data: [
      [0, 0, 80], [1, 0, 85], [2, 0, 70], [3, 0, 90], [4, 0, 75], [5, 0, 88], [6, 0, 82],
      [0, 1, 65], [1, 1, 70], [2, 1, 60], [3, 1, 78], [4, 1, 55], [5, 1, 72], [6, 1, 68],
      [0, 2, 75], [1, 2, 80], [2, 2, 85], [3, 2, 88], [4, 2, 70], [5, 2, 90], [6, 2, 85],
    ],
    label: { show: true, fontSize: 11 },
    emphasis: { itemStyle: { shadowBlur: 10, shadowColor: 'rgba(0, 0, 0, 0.3)' } }
  }]
})

const statCards = [
  { label: '今日学习时长', value: '127', suffix: '分钟', icon: Clock, color: '#4F46E5', bg: '#EEF2FF', trend: '+12%', trendUp: true },
  { label: '完成任务', value: '42', suffix: '个', icon: CircleCheck, color: '#22C55E', bg: '#F0FDF4', trend: '+5', trendUp: true },
  { label: '连续学习天数', value: '6', suffix: '天', icon: Calendar, color: '#F59E0B', bg: '#FFFBEB', trend: '本周', trendUp: true },
  { label: '当前排名', value: '12', suffix: '名', icon: Trophy, color: '#EF4444', bg: '#FEF2F2', trend: '-3', trendUp: true }
]

const subjectProgress = [
  { name: '语文', value: 85, color: '#F97316', icon: Reading, lessons: '12 课时' },
  { name: '数学', value: 72, color: '#7C3AED', icon: Aim, lessons: '8 课时' },
  { name: '英语', value: 90, color: '#06B6D4', icon: ChatLineRound, lessons: '15 课时' }
]

const todayTasks = [
  { id: 1, title: '完成《岳阳楼记》阅读理解', subject: '语文', subjectColor: '#F97316', deadline: '今天 18:00', status: 'pending' },
  { id: 2, title: '数学错题本练习 - 函数图像', subject: '数学', subjectColor: '#7C3AED', deadline: '今天 21:00', status: 'pending' },
  { id: 3, title: '英语词汇测试 Unit 5', subject: '英语', subjectColor: '#06B6D4', deadline: '已完成', status: 'done' },
  { id: 4, title: 'AI口语对练 - 日常对话', subject: '英语', subjectColor: '#06B6D4', deadline: '今天 22:00', status: 'pending' }
]

const quickEntries = [
  { label: '文言文剧场', icon: Reading, color: '#F97316', bg: '#FFF7ED', path: '/chinese/wenyanwen' },
  { label: '错题本', icon: Collection, color: '#7C3AED', bg: '#F5F3FF', path: '/math/wrong-questions' },
  { label: '词汇记忆', icon: Notebook, color: '#06B6D4', bg: '#ECFEFF', path: '/english/vocabulary' },
  { label: '排行榜', icon: Trophy, color: '#EF4444', bg: '#FEF2F2', path: '/dashboard/ranking' }
]
</script>

<template>
  <div class="dashboard-overview">
    <PageHeader title="学习数据看板" subtitle="全面掌握你的学习情况" />

    <!-- ============ Bento Grid 主区域 ============ -->
    <div class="bento-grid">
      <!-- 主统计卡片 x4 -->
      <div
        v-for="(stat, idx) in statCards"
        :key="idx"
        class="bento-card stat-card"
      >
        <div class="stat-card-top">
          <div class="stat-icon" :style="{ background: stat.bg, color: stat.color }">
            <component :is="stat.icon" :size="22" />
          </div>
          <span class="stat-trend" :class="stat.trendUp ? 'trend-up' : 'trend-down'">
            <ArrowUp v-if="stat.trendUp" :size="12" />
            {{ stat.trend }}
          </span>
        </div>
        <div class="stat-card-body">
          <span class="stat-label">{{ stat.label }}</span>
          <div class="stat-value-row">
            <span class="stat-value" :style="{ color: stat.color }">{{ stat.value }}</span>
            <span class="stat-suffix">{{ stat.suffix }}</span>
          </div>
        </div>
        <div class="stat-card-bg" :style="{ background: stat.bg }"></div>
      </div>

      <!-- 学习趋势图（大卡片） -->
      <div class="bento-card chart-card chart-card-large">
        <div class="card-header">
          <div class="card-header-left">
            <div class="card-header-icon icon-primary">
              <TrendCharts :size="18" />
            </div>
            <div>
              <h3 class="card-title">本周学习趋势</h3>
              <p class="card-subtitle">学习时长与正确率对比</p>
            </div>
          </div>
          <span class="card-badge badge-success">
            <ArrowUp :size="12" />
            +15% 较上周
          </span>
        </div>
        <ChartPanel :option="lineOption" height="320px" />
      </div>

      <!-- 学科正确率图（中卡片） -->
      <div class="bento-card chart-card chart-card-medium">
        <div class="card-header">
          <div class="card-header-left">
            <div class="card-header-icon icon-orange">
              <Aim :size="18" />
            </div>
            <div>
              <h3 class="card-title">学科正确率</h3>
              <p class="card-subtitle">各学科平均掌握情况</p>
            </div>
          </div>
        </div>
        <ChartPanel :option="barOption" height="280px" />
      </div>

      <!-- 学科进度卡（中卡片） -->
      <div class="bento-card subject-progress-card">
        <div class="card-header">
          <div class="card-header-left">
            <div class="card-header-icon icon-purple">
              <Notebook :size="18" />
            </div>
            <div>
              <h3 class="card-title">学科进度</h3>
              <p class="card-subtitle">各学科学习完成度</p>
            </div>
          </div>
        </div>
        <div class="subject-progress-list">
          <div v-for="subject in subjectProgress" :key="subject.name" class="subject-progress-item">
            <div class="subject-info-row">
              <div class="subject-icon-mini" :style="{ background: subject.color + '14', color: subject.color }">
                <component :is="subject.icon" :size="16" />
              </div>
              <span class="subject-name">{{ subject.name }}</span>
              <span class="subject-lessons">{{ subject.lessons }}</span>
              <span class="subject-percent" :style="{ color: subject.color }">{{ subject.value }}%</span>
            </div>
            <div class="progress-bar-custom">
              <div
                class="progress-fill-custom"
                :style="{ width: subject.value + '%', background: `linear-gradient(90deg, ${subject.color}, ${subject.color}CC)` }"
              ></div>
            </div>
          </div>
        </div>
      </div>

      <!-- 知识掌握热力图（大卡片） -->
      <div class="bento-card chart-card chart-card-large">
        <div class="card-header">
          <div class="card-header-left">
            <div class="card-header-icon icon-cyan">
              <TrendCharts :size="18" />
            </div>
            <div>
              <h3 class="card-title">知识掌握热力图</h3>
              <p class="card-subtitle">本周各学科每日掌握度</p>
            </div>
          </div>
        </div>
        <ChartPanel :option="heatmapOption" height="280px" />
      </div>

      <!-- 今日任务（中卡片） -->
      <div class="bento-card task-card">
        <div class="card-header">
          <div class="card-header-left">
            <div class="card-header-icon icon-green">
              <CircleCheck :size="18" />
            </div>
            <div>
              <h3 class="card-title">今日任务</h3>
              <p class="card-subtitle">{{ todayTasks.filter(t => t.status === 'pending').length }} 项待完成</p>
            </div>
          </div>
        </div>
        <div class="task-list">
          <div
            v-for="task in todayTasks"
            :key="task.id"
            class="task-item"
            :class="{ 'task-done': task.status === 'done' }"
          >
            <div class="task-checkbox">
              <CircleCheck v-if="task.status === 'done'" :size="20" color="#22C55E" />
              <div v-else class="task-checkbox-empty"></div>
            </div>
            <div class="task-content">
              <span class="task-title">{{ task.title }}</span>
              <div class="task-meta">
                <span class="task-subject-tag" :style="{ background: task.subjectColor + '14', color: task.subjectColor }">
                  {{ task.subject }}
                </span>
                <span class="task-deadline" :class="{ 'deadline-done': task.status === 'done' }">
                  {{ task.deadline }}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 快捷入口卡 -->
      <div class="bento-card quick-entry-card">
        <div class="card-header">
          <div class="card-header-left">
            <div class="card-header-icon icon-primary">
              <ArrowRight :size="18" />
            </div>
            <div>
              <h3 class="card-title">快捷入口</h3>
              <p class="card-subtitle">一键直达常用功能</p>
            </div>
          </div>
        </div>
        <div class="quick-entry-grid">
          <div
            v-for="entry in quickEntries"
            :key="entry.label"
            class="quick-entry-item"
            @click="$router.push(entry.path)"
          >
            <div class="quick-entry-icon" :style="{ background: entry.bg, color: entry.color }">
              <component :is="entry.icon" :size="22" />
            </div>
            <span class="quick-entry-label">{{ entry.label }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dashboard-overview {
  font-family: var(--font-body);
  color: var(--text-primary);
}

/* ============ Bento Grid Layout ============ */
.bento-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.bento-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 24px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
}

.bento-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

/* ============ Stat Cards ============ */
.stat-card {
  grid-column: span 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-height: 140px;
}

.stat-card-top {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  position: relative;
  z-index: 1;
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-trend {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 700;
  padding: 4px 8px;
  border-radius: var(--radius-full);
}

.trend-up {
  background: var(--success-50);
  color: var(--success-dark);
}

.trend-down {
  background: var(--danger-50);
  color: var(--danger-dark);
}

.stat-card-body {
  position: relative;
  z-index: 1;
}

.stat-label {
  display: block;
  font-size: 13px;
  color: var(--text-secondary);
  margin-bottom: 8px;
  font-weight: 500;
}

.stat-value-row {
  display: flex;
  align-items: baseline;
  gap: 6px;
}

.stat-value {
  font-family: var(--font-heading);
  font-size: 32px;
  font-weight: 800;
  line-height: 1;
  letter-spacing: -0.02em;
}

.stat-suffix {
  font-size: 14px;
  color: var(--text-secondary);
  font-weight: 500;
}

.stat-card-bg {
  position: absolute;
  bottom: -40px;
  right: -40px;
  width: 120px;
  height: 120px;
  border-radius: 50%;
  opacity: 0.5;
  z-index: 0;
}

/* ============ Chart Cards ============ */
.chart-card {
  display: flex;
  flex-direction: column;
}

.chart-card-large {
  grid-column: span 3;
}

.chart-card-medium {
  grid-column: span 1;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.card-header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.card-header-icon {
  width: 36px;
  height: 36px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.icon-primary { background: linear-gradient(135deg, #4F46E5, #818CF8); }
.icon-orange { background: linear-gradient(135deg, #F97316, #FB923C); }
.icon-purple { background: linear-gradient(135deg, #7C3AED, #A78BFA); }
.icon-cyan { background: linear-gradient(135deg, #06B6D4, #22D3EE); }
.icon-green { background: linear-gradient(135deg, #22C55E, #4ADE80); }

.card-title {
  font-family: var(--font-heading);
  font-size: 16px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 2px;
}

.card-subtitle {
  font-size: 12px;
  color: var(--text-secondary);
}

.card-badge {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 600;
  padding: 6px 12px;
  border-radius: var(--radius-full);
}

.badge-success {
  background: var(--success-50);
  color: var(--success-dark);
}

/* Override ChartPanel internal styling */
.chart-card :deep(.chart-panel) {
  background: transparent;
  box-shadow: none;
  border: none;
  padding: 0;
}

.chart-card :deep(.chart-panel__canvas) {
  min-height: auto;
}

/* ============ Subject Progress Card ============ */
.subject-progress-card {
  grid-column: span 1;
}

.subject-progress-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.subject-progress-item {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.subject-info-row {
  display: flex;
  align-items: center;
  gap: 10px;
}

.subject-icon-mini {
  width: 28px;
  height: 28px;
  border-radius: var(--radius-sm);
  display: flex;
  align-items: center;
  justify-content: center;
}

.subject-name {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
}

.subject-lessons {
  font-size: 12px;
  color: var(--text-muted);
  flex: 1;
}

.subject-percent {
  font-family: var(--font-heading);
  font-size: 14px;
  font-weight: 700;
}

.progress-bar-custom {
  height: 8px;
  background: var(--bg-muted);
  border-radius: var(--radius-full);
  overflow: hidden;
}

.progress-fill-custom {
  height: 100%;
  border-radius: var(--radius-full);
  transition: width var(--transition-slow);
}

/* ============ Heatmap card spans 3 columns ============ */
.bento-card.chart-card-large:nth-of-type(6) {
  grid-column: span 3;
}

/* ============ Task Card ============ */
.task-card {
  grid-column: span 2;
}

.task-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.task-item {
  display: flex;
  gap: 12px;
  padding: 14px;
  background: var(--bg-muted);
  border-radius: var(--radius-md);
  transition: all var(--transition-normal);
  cursor: pointer;
  border: 1px solid transparent;
}

.task-item:hover {
  background: var(--primary-50);
  border-color: var(--primary-200);
}

.task-item.task-done {
  opacity: 0.6;
}

.task-checkbox {
  flex-shrink: 0;
  margin-top: 2px;
}

.task-checkbox-empty {
  width: 20px;
  height: 20px;
  border: 2px solid var(--border-dark);
  border-radius: var(--radius-full);
  background: var(--bg-card);
  transition: all var(--transition-fast);
}

.task-checkbox-empty:hover {
  border-color: var(--primary);
}

.task-content {
  flex: 1;
  min-width: 0;
}

.task-title {
  display: block;
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.task-done .task-title {
  text-decoration: line-through;
  color: var(--text-muted);
}

.task-meta {
  display: flex;
  align-items: center;
  gap: 12px;
}

.task-subject-tag {
  font-size: 11px;
  font-weight: 700;
  padding: 2px 8px;
  border-radius: var(--radius-sm);
}

.task-deadline {
  font-size: 12px;
  color: var(--text-muted);
}

.deadline-done {
  color: var(--success);
}

/* ============ Quick Entry Card ============ */
.quick-entry-card {
  grid-column: span 2;
}

.quick-entry-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.quick-entry-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background: var(--bg-muted);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-normal);
  border: 1px solid transparent;
}

.quick-entry-item:hover {
  background: var(--bg-card);
  border-color: var(--primary-200);
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.quick-entry-icon {
  width: 44px;
  height: 44px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
}

.quick-entry-label {
  font-size: 13px;
  font-weight: 600;
  color: var(--text-primary);
}

/* ============ Responsive ============ */
@media (max-width: 1024px) {
  .bento-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .stat-card {
    grid-column: span 1;
  }

  .chart-card-large {
    grid-column: span 2;
  }

  .chart-card-medium,
  .subject-progress-card,
  .task-card,
  .quick-entry-card {
    grid-column: span 2;
  }

  .bento-card.chart-card-large:nth-of-type(6) {
    grid-column: span 2;
  }
}

@media (max-width: 768px) {
  .bento-grid {
    grid-template-columns: 1fr;
  }

  .stat-card,
  .chart-card-large,
  .chart-card-medium,
  .subject-progress-card,
  .task-card,
  .quick-entry-card,
  .bento-card.chart-card-large:nth-of-type(6) {
    grid-column: span 1;
  }

  .quick-entry-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .bento-card {
    padding: 16px;
  }
}
</style>
