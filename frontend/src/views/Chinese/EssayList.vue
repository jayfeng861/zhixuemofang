<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import EmptyState from '@/components/EmptyState.vue'

const router = useRouter()

const essays = ref([
  { id: 1, title: '春天的脚步', type: '记叙文', score: 82, date: '2024-03-15', status: '已批改' },
  { id: 2, title: '我的梦想', type: '议论文', score: 88, date: '2024-03-10', status: '已批改' },
  { id: 3, title: '一次难忘的旅行', type: '散文', score: 0, date: '2024-03-08', status: '待批改' },
])
</script>

<template>
  <div class="page-container">
    <PageHeader title="我的作文" subtitle="查看AI批改结果和写作记录">
      <template #actions>
        <el-button type="primary" class="action-btn">
          <el-icon><EditPen /></el-icon>
          <span>写新作文</span>
        </el-button>
      </template>
    </PageHeader>

    <div v-if="essays.length > 0" class="essay-list">
      <div v-for="item in essays" :key="item.id" class="essay-item card card-clickable" @click="router.push(`/chinese/essay/${item.id}`)">
        <div class="essay-icon-wrap" :class="item.status == '已批改' ? 'graded' : 'pending'">
          <el-icon><Document /></el-icon>
        </div>
        <div class="essay-info">
          <h3>{{ item.title }}</h3>
          <div class="essay-meta">
            <span class="essay-type">{{ item.type }}</span>
            <span class="essay-date">
              <el-icon><Calendar /></el-icon>
              {{ item.date }}
            </span>
          </div>
        </div>
        <div class="essay-score-area">
          <div v-if="item.score > 0" class="score-badge" :class="item.score >= 80 ? 'good' : 'medium'">
            <span class="score-num">{{ item.score }}</span>
            <span class="score-unit">分</span>
          </div>
          <span v-else class="status-tag pending">
            <el-icon><Clock /></el-icon>
            待批改
          </span>
        </div>
        <div class="essay-arrow">
          <el-icon><ArrowRight /></el-icon>
        </div>
      </div>
    </div>
    <EmptyState v-else title="暂无作文" description="快去写一篇作文试试吧" />
  </div>
</template>

<style scoped>
.action-btn {
  --el-button-bg-color: var(--chinese-primary) !important;
  --el-button-border-color: var(--chinese-primary) !important;
  --el-button-hover-bg-color: #EA580C !important;
  --el-button-hover-border-color: #EA580C !important;
  --el-button-active-bg-color: #C2410C !important;
  --el-button-active-border-color: #C2410C !important;
  box-shadow: var(--chinese-shadow) !important;
}

.essay-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.essay-item {
  display: flex;
  align-items: center;
  gap: 16px;
}

.essay-icon-wrap {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  flex-shrink: 0;
}

.essay-icon-wrap.graded {
  background: var(--chinese-light);
  color: var(--chinese-primary);
}

.essay-icon-wrap.pending {
  background: var(--warning-50);
  color: var(--warning-dark);
}

.essay-info {
  flex: 1;
  min-width: 0;
}

.essay-info h3 {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 6px;
  color: var(--text-primary);
}

.essay-meta {
  display: flex;
  gap: 16px;
  align-items: center;
}

.essay-type {
  font-size: 13px;
  color: var(--chinese-primary);
  font-weight: 500;
  padding: 2px 10px;
  background: var(--chinese-light);
  border-radius: var(--radius-full);
}

.essay-date {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: var(--text-muted);
}

.essay-score-area {
  flex-shrink: 0;
}

.score-badge {
  display: inline-flex;
  align-items: baseline;
  gap: 2px;
  padding: 6px 16px;
  border-radius: var(--radius-md);
}

.score-badge.good {
  background: var(--success-50);
}

.score-badge.medium {
  background: var(--warning-50);
}

.score-num {
  font-size: 28px;
  font-weight: 700;
  line-height: 1;
}

.score-badge.good .score-num {
  color: var(--success-dark);
}

.score-badge.medium .score-num {
  color: var(--warning-dark);
}

.score-unit {
  font-size: 13px;
  font-weight: 600;
}

.score-badge.good .score-unit {
  color: var(--success-dark);
}

.score-badge.medium .score-unit {
  color: var(--warning-dark);
}

.status-tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 6px 14px;
  border-radius: var(--radius-full);
  font-size: 13px;
  font-weight: 600;
}

.status-tag.pending {
  background: var(--warning-50);
  color: var(--warning-dark);
}

.essay-arrow {
  color: var(--text-muted);
  font-size: 18px;
  flex-shrink: 0;
}

@media (max-width: 640px) {
  .essay-item {
    flex-wrap: wrap;
    gap: 12px;
  }

  .essay-arrow {
    display: none;
  }

  .essay-meta {
    flex-wrap: wrap;
    gap: 8px;
  }
}
</style>
