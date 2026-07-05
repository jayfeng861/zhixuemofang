<script setup lang="ts">
import { ref, onMounted } from 'vue'
import {
  User,
  UserFilled,
  Reading,
  Clock,
  Top,
  Bottom,
  DataLine,
  Histogram,
  Coin,
  Calendar
} from '@element-plus/icons-vue'

const loading = ref(false)

const stats = ref([
  {
    title: '总学生数',
    value: '12,845',
    change: '+12.5%',
    trend: 'up',
    icon: User,
    color: 'primary'
  },
  {
    title: '总教师数',
    value: '1,256',
    change: '+8.2%',
    trend: 'up',
    icon: UserFilled,
    color: 'success'
  },
  {
    title: '今日活跃',
    value: '3,428',
    change: '+15.3%',
    trend: 'up',
    icon: Reading,
    color: 'warning'
  },
  {
    title: '学习时长(小时)',
    value: '8,924',
    change: '-2.1%',
    trend: 'down',
    icon: Clock,
    color: 'danger'
  }
])

const recentActivities = ref([
  { id: 1, user: '张三', action: '完成了文言文练习', time: '5分钟前', type: 'chinese' },
  { id: 2, user: '李四', action: '开始了几何实验', time: '12分钟前', type: 'math' },
  { id: 3, user: '王五', action: '提交了作文', time: '25分钟前', type: 'chinese' },
  { id: 4, user: '赵六', action: '完成了口语练习', time: '32分钟前', type: 'english' },
  { id: 5, user: '孙七', action: '加入了学习社区', time: '1小时前', type: 'community' }
])

const subjectStats = ref([
  { name: '语文', value: 3520, percentage: 32 },
  { name: '数学', value: 2890, percentage: 26 },
  { name: '英语', value: 3150, percentage: 28 },
  { name: '其他', value: 1540, percentage: 14 }
])

const weeklyData = ref([
  { day: '周一', students: 2400, teachers: 320 },
  { day: '周二', students: 2800, teachers: 380 },
  { day: '周三', students: 2600, teachers: 350 },
  { day: '周四', students: 3100, teachers: 410 },
  { day: '周五', students: 3400, teachers: 450 },
  { day: '周六', students: 4200, teachers: 280 },
  { day: '周日', students: 3800, teachers: 220 }
])

function getActivityIcon(type: string) {
  const icons: Record<string, any> = {
    chinese: Reading,
    math: DataLine,
    english: Coin,
    community: Histogram
  }
  return icons[type] || User
}

function getActivityColor(type: string) {
  const colors: Record<string, string> = {
    chinese: '#F97316',
    math: '#7C3AED',
    english: '#06B6D4',
    community: '#22C55E'
  }
  return colors[type] || '#4F46E5'
}

onMounted(() => {})
</script>

<template>
  <div class="dashboard-page">
    <div class="page-header">
      <div>
        <h1 class="page-title">数据概览</h1>
        <p class="page-subtitle">查看平台整体运营数据和用户活跃度</p>
      </div>
      <div class="header-actions">
        <el-button class="date-btn">
          <el-icon><Calendar /></el-icon>
          <span>今日数据</span>
        </el-button>
      </div>
    </div>

    <div class="stats-grid">
      <div
        v-for="(stat, index) in stats"
        :key="index"
        class="stat-card"
        :class="`stat-${stat.color}`"
      >
        <div class="stat-header">
          <div class="stat-icon">
            <el-icon><component :is="stat.icon" /></el-icon>
          </div>
          <div class="stat-trend" :class="stat.trend">
            <el-icon><component :is="stat.trend === 'up' ? Top : Bottom" /></el-icon>
            <span>{{ stat.change }}</span>
          </div>
        </div>
        <div class="stat-value">{{ stat.value }}</div>
        <div class="stat-title">{{ stat.title }}</div>
      </div>
    </div>

    <div class="charts-grid">
      <div class="chart-card">
        <div class="chart-header">
          <h3 class="chart-title">周活跃趋势</h3>
          <div class="chart-tags">
            <span class="tag tag-primary">学生</span>
            <span class="tag tag-success">教师</span>
          </div>
        </div>
        <div class="chart-body">
          <div class="bar-chart">
            <div v-for="(item, index) in weeklyData" :key="index" class="bar-group">
              <div class="bar-wrapper">
                <div
                  class="bar bar-primary"
                  :style="{ height: `${(item.students / 5000) * 100}%` }"
                ></div>
                <div
                  class="bar bar-success"
                  :style="{ height: `${(item.teachers / 500) * 100}%` }"
                ></div>
              </div>
              <div class="bar-label">{{ item.day }}</div>
            </div>
          </div>
        </div>
      </div>

      <div class="chart-card">
        <div class="chart-header">
          <h3 class="chart-title">学科分布</h3>
        </div>
        <div class="chart-body">
          <div class="donut-chart">
            <div class="donut-inner">
              <div class="donut-value">11,100</div>
              <div class="donut-label">总学习次数</div>
            </div>
          </div>
          <div class="legend-list">
            <div
              v-for="(item, index) in subjectStats"
              :key="index"
              class="legend-item"
            >
              <div class="legend-dot" :class="`dot-${index}`"></div>
              <span class="legend-name">{{ item.name }}</span>
              <span class="legend-value">{{ item.value }}</span>
              <span class="legend-percent">{{ item.percentage }}%</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="bottom-section">
      <div class="activity-card">
        <div class="card-header">
          <h3 class="card-title">最近动态</h3>
          <el-button type="primary" link class="view-more">查看全部</el-button>
        </div>
        <div class="activity-list">
          <div
            v-for="activity in recentActivities"
            :key="activity.id"
            class="activity-item"
          >
            <div
              class="activity-icon"
              :style="{ background: `${getActivityColor(activity.type)}15`, color: getActivityColor(activity.type) }"
            >
              <el-icon><component :is="getActivityIcon(activity.type)" /></el-icon>
            </div>
            <div class="activity-content">
              <div class="activity-text">
                <span class="activity-user">{{ activity.user }}</span>
                {{ activity.action }}
              </div>
              <div class="activity-time">{{ activity.time }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dashboard-page {
  width: 100%;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 24px;
}

.page-title {
  font-family: var(--font-heading);
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 6px;
}

.page-subtitle {
  font-size: 14px;
  color: var(--text-secondary);
  margin: 0;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.date-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  border-radius: var(--radius-md) !important;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}

.stat-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 24px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  transition: all var(--transition-normal);
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.stat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.stat-primary .stat-icon {
  background: var(--primary-50);
  color: var(--primary);
}

.stat-success .stat-icon {
  background: var(--success-50);
  color: var(--success);
}

.stat-warning .stat-icon {
  background: var(--warning-50);
  color: var(--warning);
}

.stat-danger .stat-icon {
  background: var(--danger-50);
  color: var(--danger);
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  font-weight: 600;
}

.stat-trend.up {
  color: var(--success);
}

.stat-trend.down {
  color: var(--danger);
}

.stat-trend .el-icon {
  font-size: 14px;
}

.stat-value {
  font-family: var(--font-heading);
  font-size: 32px;
  font-weight: 800;
  color: var(--text-primary);
  line-height: 1.2;
  margin-bottom: 6px;
}

.stat-title {
  font-size: 14px;
  color: var(--text-secondary);
}

.charts-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
  margin-bottom: 24px;
}

.chart-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 24px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
}

.chart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.chart-title {
  font-family: var(--font-heading);
  font-size: 18px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0;
}

.chart-tags {
  display: flex;
  gap: 8px;
}

.tag {
  padding: 4px 10px;
  border-radius: var(--radius-full);
  font-size: 12px;
  font-weight: 500;
}

.tag-primary {
  background: var(--primary-50);
  color: var(--primary);
}

.tag-success {
  background: var(--success-50);
  color: var(--success);
}

.chart-body {
  height: 280px;
}

.bar-chart {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  height: 100%;
  gap: 12px;
  padding-bottom: 32px;
  position: relative;
}

.bar-group {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.bar-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: flex-end;
  justify-content: center;
  gap: 4px;
}

.bar {
  width: 20px;
  border-radius: var(--radius-xs) var(--radius-xs) 0 0;
  transition: all var(--transition-normal);
  cursor: pointer;
}

.bar:hover {
  opacity: 0.8;
}

.bar-primary {
  background: linear-gradient(to top, var(--primary), var(--primary-light));
}

.bar-success {
  background: linear-gradient(to top, var(--success), var(--success-light));
}

.bar-label {
  font-size: 12px;
  color: var(--text-muted);
  position: absolute;
  bottom: 0;
}

.donut-chart {
  width: 180px;
  height: 180px;
  margin: 0 auto 24px;
  border-radius: 50%;
  background: conic-gradient(
    #F97316 0deg 115deg,
    #7C3AED 115deg 209deg,
    #06B6D4 209deg 309deg,
    #94A3B8 309deg 360deg
  );
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.donut-inner {
  width: 130px;
  height: 130px;
  background: var(--bg-card);
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.donut-value {
  font-family: var(--font-heading);
  font-size: 24px;
  font-weight: 800;
  color: var(--text-primary);
}

.donut-label {
  font-size: 12px;
  color: var(--text-muted);
  margin-top: 2px;
}

.legend-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
}

.legend-dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  flex-shrink: 0;
}

.dot-0 { background: #F97316; }
.dot-1 { background: #7C3AED; }
.dot-2 { background: #06B6D4; }
.dot-3 { background: #94A3B8; }

.legend-name {
  color: var(--text-secondary);
  flex: 1;
}

.legend-value {
  color: var(--text-primary);
  font-weight: 600;
}

.legend-percent {
  color: var(--text-muted);
  width: 48px;
  text-align: right;
}

.bottom-section {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
}

.activity-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 24px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.card-title {
  font-family: var(--font-heading);
  font-size: 18px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0;
}

.view-more {
  font-size: 14px;
}

.activity-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.activity-item {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 12px;
  border-radius: var(--radius-md);
  transition: background var(--transition-fast);
  cursor: pointer;
}

.activity-item:hover {
  background: var(--bg-hover);
}

.activity-icon {
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  flex-shrink: 0;
}

.activity-content {
  flex: 1;
  min-width: 0;
}

.activity-text {
  font-size: 14px;
  color: var(--text-primary);
  margin-bottom: 4px;
}

.activity-user {
  font-weight: 600;
}

.activity-time {
  font-size: 12px;
  color: var(--text-muted);
}

@media (max-width: 1200px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .charts-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }

  .page-header {
    flex-direction: column;
    gap: 16px;
  }

  .page-title {
    font-size: 22px;
  }

  .stat-value {
    font-size: 26px;
  }
}
</style>
