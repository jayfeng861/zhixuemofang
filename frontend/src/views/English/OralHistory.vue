<script setup lang="ts">
import { ref } from 'vue'
import PageHeader from '@/components/PageHeader.vue'
import EmptyState from '@/components/EmptyState.vue'

const history = ref([
  { id: 1, topic: 'Daily Routine', date: '2024-03-15', duration: '5分钟', score: 78, messages: 8 },
  { id: 2, topic: 'My Family', date: '2024-03-14', duration: '8分钟', score: 82, messages: 12 },
  { id: 3, topic: 'School Life', date: '2024-03-12', duration: '6分钟', score: 75, messages: 10 },
  { id: 4, topic: 'Hobbies', date: '2024-03-10', duration: '4分钟', score: 80, messages: 7 },
])
</script>

<template>
  <div class="page-container">
    <PageHeader title="口语练习历史" subtitle="回顾你的口语练习记录" />

    <div v-if="history.length > 0" class="history-list">
      <div v-for="item in history" :key="item.id" class="history-item card card-clickable">
        <div class="history-icon" :class="item.score >= 80 ? 'good' : 'medium'">
          <el-icon><Microphone /></el-icon>
        </div>
        <div class="history-info">
          <h3>{{ item.topic }}</h3>
          <div class="history-meta">
            <span class="meta-item">
              <el-icon><Calendar /></el-icon>
              {{ item.date }}
            </span>
            <span class="meta-item">
              <el-icon><Clock /></el-icon>
              {{ item.duration }}
            </span>
            <span class="meta-item">
              <el-icon><ChatDotRound /></el-icon>
              {{ item.messages }} 条对话
            </span>
          </div>
        </div>
        <div class="history-score" :class="item.score >= 80 ? 'good' : 'medium'">
          <span class="score-num">{{ item.score }}</span>
          <span class="score-unit">分</span>
        </div>
        <div class="history-arrow">
          <el-icon><ArrowRight /></el-icon>
        </div>
      </div>
    </div>
    <EmptyState v-else title="暂无练习记录" description="开始你的第一次口语练习吧" />
  </div>
</template>

<style scoped>
.history-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.history-item {
  display: flex;
  align-items: center;
  gap: 16px;
}

.history-icon {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  flex-shrink: 0;
}

.history-icon.good {
  background: var(--english-light);
  color: var(--english-primary);
}

.history-icon.medium {
  background: var(--warning-50);
  color: var(--warning-dark);
}

.history-info {
  flex: 1;
  min-width: 0;
}

.history-info h3 {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 6px;
  color: var(--text-primary);
}

.history-meta {
  display: flex;
  gap: 16px;
  font-size: 13px;
  color: var(--text-muted);
  flex-wrap: wrap;
}

.meta-item {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.history-score {
  display: inline-flex;
  align-items: baseline;
  gap: 2px;
  padding: 8px 16px;
  border-radius: var(--radius-md);
  flex-shrink: 0;
}

.history-score.good {
  background: var(--success-50);
}

.history-score.medium {
  background: var(--warning-50);
}

.score-num {
  font-size: 26px;
  font-weight: 800;
  line-height: 1;
}

.history-score.good .score-num {
  color: var(--success-dark);
}

.history-score.medium .score-num {
  color: var(--warning-dark);
}

.score-unit {
  font-size: 13px;
  font-weight: 600;
}

.history-score.good .score-unit {
  color: var(--success-dark);
}

.history-score.medium .score-unit {
  color: var(--warning-dark);
}

.history-arrow {
  color: var(--text-muted);
  font-size: 18px;
  flex-shrink: 0;
}

@media (max-width: 640px) {
  .history-item {
    flex-wrap: wrap;
    gap: 12px;
  }

  .history-arrow {
    display: none;
  }

  .history-meta {
    gap: 10px;
  }
}
</style>
