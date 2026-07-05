<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import EmptyState from '@/components/EmptyState.vue'

const router = useRouter()
const activeSubject = ref('all')

const wrongQuestions = ref([
  { id: 1, subject: '代数', topic: '一元二次方程', question: '解方程 x²-5x+6=0', difficulty: '中等', wrongCount: 2, date: '2024-03-15' },
  { id: 2, subject: '几何', topic: '全等三角形', question: '证明三角形ABC全等于DEF', difficulty: '较难', wrongCount: 3, date: '2024-03-14' },
  { id: 3, subject: '代数', topic: '一次函数', question: '求函数 y=2x+1 与 y=-x+4 的交点', difficulty: '简单', wrongCount: 1, date: '2024-03-12' },
  { id: 4, subject: '几何', topic: '圆的性质', question: '已知圆的半径为5，弦长为8，求弦心距', difficulty: '中等', wrongCount: 2, date: '2024-03-10' },
  { id: 5, subject: '代数', topic: '不等式', question: '解不等式组 2x-1>3 且 x+2<8', difficulty: '简单', wrongCount: 1, date: '2024-03-08' },
])

const subjects = ['all', '代数', '几何']
</script>

<template>
  <div class="page-container">
    <PageHeader title="错题本" subtitle="记录每一次错误，成就每一次进步">
      <template #actions>
        <el-button type="primary" class="action-btn" @click="router.push('/math/wrong-analysis')">
          <el-icon><DataAnalysis /></el-icon>
          <span>错题溯源分析</span>
        </el-button>
      </template>
    </PageHeader>

    <div class="filter-bar">
      <div class="filter-group">
        <span v-for="s in subjects" :key="s" class="filter-tag" :class="{ active: activeSubject == s }" @click="activeSubject = s">
          {{ s == 'all' ? '全部' : s }}
        </span>
      </div>
      <div class="wrong-count">
        <el-icon><WarningFilled /></el-icon>
        共 {{ wrongQuestions.length }} 道错题
      </div>
    </div>

    <div v-if="wrongQuestions.length > 0" class="wrong-list">
      <div v-for="item in wrongQuestions" :key="item.id" class="wrong-item card card-clickable" @click="router.push('/math/wrong-analysis')">
        <div class="wrong-info">
          <div class="wrong-header">
            <span class="wrong-subject" :class="item.subject == '代数' ? 'algebra' : 'geometry'">{{ item.subject }}</span>
            <span class="wrong-topic">{{ item.topic }}</span>
            <span class="wrong-difficulty" :class="item.difficulty == '简单' ? 'easy' : item.difficulty == '中等' ? 'medium' : 'hard'">{{ item.difficulty }}</span>
          </div>
          <p class="wrong-question">{{ item.question }}</p>
          <div class="wrong-meta">
            <span class="meta-item">
              <el-icon><Refresh /></el-icon>
              错 {{ item.wrongCount }} 次
            </span>
            <span class="meta-item">
              <el-icon><Calendar /></el-icon>
              {{ item.date }}
            </span>
          </div>
        </div>
        <div class="wrong-action">
          <el-icon><ArrowRight /></el-icon>
        </div>
      </div>
    </div>
    <EmptyState v-else title="暂无错题" description="继续保持，做对的题比错题更重要" />
  </div>
</template>

<style scoped>
.action-btn {
  --el-button-bg-color: var(--math-primary) !important;
  --el-button-border-color: var(--math-primary) !important;
  --el-button-hover-bg-color: #6D28D9 !important;
  --el-button-hover-border-color: #6D28D9 !important;
  --el-button-active-bg-color: #5B21B6 !important;
  --el-button-active-border-color: #5B21B6 !important;
  box-shadow: var(--math-shadow) !important;
}

.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 12px;
}

.filter-group {
  display: flex;
  gap: 8px;
}

.filter-tag {
  padding: 6px 16px;
  border-radius: var(--radius-full);
  font-size: 13px;
  color: var(--text-secondary);
  cursor: pointer;
  background: var(--bg-card);
  border: 1px solid var(--border);
  transition: all var(--transition-fast);
}

.filter-tag:hover {
  color: var(--math-primary);
  border-color: var(--math-primary);
}

.filter-tag.active {
  color: white;
  background: var(--math-primary);
  border-color: var(--math-primary);
  box-shadow: var(--math-shadow);
}

.wrong-count {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: var(--text-muted);
}

.wrong-count .el-icon {
  color: var(--math-primary);
}

.wrong-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.wrong-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
}

.wrong-info {
  flex: 1;
  min-width: 0;
}

.wrong-header {
  display: flex;
  gap: 8px;
  margin-bottom: 8px;
  align-items: center;
  flex-wrap: wrap;
}

.wrong-subject {
  padding: 3px 12px;
  border-radius: var(--radius-full);
  font-size: 12px;
  font-weight: 700;
}

.wrong-subject.algebra {
  background: var(--math-light);
  color: var(--math-primary);
}

.wrong-subject.geometry {
  background: var(--primary-50);
  color: var(--primary);
}

.wrong-topic {
  font-size: 13px;
  color: var(--text-secondary);
  font-weight: 500;
}

.wrong-difficulty {
  padding: 2px 10px;
  border-radius: var(--radius-full);
  font-size: 11px;
  font-weight: 600;
}

.wrong-difficulty.easy {
  background: var(--success-50);
  color: var(--success-dark);
}

.wrong-difficulty.medium {
  background: var(--warning-50);
  color: var(--warning-dark);
}

.wrong-difficulty.hard {
  background: var(--danger-50);
  color: var(--danger-dark);
}

.wrong-question {
  font-size: 15px;
  color: var(--text-primary);
  margin-bottom: 10px;
  font-weight: 500;
}

.wrong-meta {
  display: flex;
  gap: 16px;
  font-size: 12px;
  color: var(--text-muted);
}

.meta-item {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.wrong-action {
  color: var(--text-muted);
  font-size: 18px;
  flex-shrink: 0;
}

@media (max-width: 640px) {
  .wrong-item {
    flex-direction: column;
    align-items: flex-start;
  }

  .wrong-action {
    align-self: flex-end;
  }
}
</style>
