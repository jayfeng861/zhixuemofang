<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import EmptyState from '@/components/EmptyState.vue'

const router = useRouter()
const loading = ref(false)

const wenyanwenList = ref([
  { id: 1, title: '桃花源记', author: '陶渊明', dynasty: '东晋', difficulty: '中等', students: 2340, cover: '#E74C3C' },
  { id: 2, title: '岳阳楼记', author: '范仲淹', dynasty: '北宋', difficulty: '较难', students: 1890, cover: '#F39C12' },
  { id: 3, title: '醉翁亭记', author: '欧阳修', dynasty: '北宋', difficulty: '中等', students: 1560, cover: '#E67E22' },
  { id: 4, title: '出师表', author: '诸葛亮', dynasty: '三国', difficulty: '较难', students: 2100, cover: '#C0392B' },
  { id: 5, title: '陋室铭', author: '刘禹锡', dynasty: '唐代', difficulty: '简单', students: 3200, cover: '#D35400' },
  { id: 6, title: '小石潭记', author: '柳宗元', dynasty: '唐代', difficulty: '中等', students: 1750, cover: '#E74C3C' },
])

const filterDifficulty = ref('all')
const difficulties = [
  { label: '全部', value: 'all' },
  { label: '简单', value: '简单' },
  { label: '中等', value: '中等' },
  { label: '较难', value: '较难' },
]

const filteredList = ref(wenyanwenList.value)
</script>

<template>
  <div class="page-container">
    <PageHeader title="文言文剧场" subtitle="穿越千年，身临其境感受古文之美">
      <template #actions>
        <el-button type="primary" class="action-btn">
          <el-icon><Plus /></el-icon>
          <span>添加篇目</span>
        </el-button>
      </template>
    </PageHeader>

    <div class="filter-bar">
      <div class="filter-group">
        <span
          v-for="item in difficulties"
          :key="item.value"
          class="filter-tag"
          :class="{ active: filterDifficulty == item.value }"
          @click="filterDifficulty = item.value"
        >{{ item.label }}</span>
      </div>
      <div class="filter-count">共 {{ wenyanwenList.length }} 篇</div>
    </div>

    <div v-loading="loading" class="wenyanwen-grid">
      <div
        v-for="item in wenyanwenList"
        :key="item.id"
        class="wenyanwen-card card card-clickable"
        @click="router.push(`/chinese/wenyanwen/${item.id}`)"
      >
        <div class="card-cover" :style="{ background: `linear-gradient(135deg, ${item.cover}, ${item.cover}99)` }">
          <el-icon class="cover-icon"><Document /></el-icon>
          <span class="cover-dynasty">{{ item.dynasty }}</span>
        </div>
        <div class="card-body">
          <h3>{{ item.title }}</h3>
          <p class="card-meta">{{ item.dynasty }} · {{ item.author }}</p>
          <div class="card-footer">
            <span class="difficulty-tag" :class="item.difficulty == '简单' ? 'easy' : item.difficulty == '中等' ? 'medium' : 'hard'">
              {{ item.difficulty }}
            </span>
            <span class="student-count">
              <el-icon><User /></el-icon>
              {{ item.students }}人在学
            </span>
          </div>
        </div>
      </div>
    </div>

    <EmptyState v-if="!loading && wenyanwenList.length == 0" title="暂无文言文" description="更多精彩内容即将上线" />
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

.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
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
  transition: all var(--transition-fast);
  background: var(--bg-card);
  border: 1px solid var(--border);
}

.filter-tag:hover {
  color: var(--chinese-primary);
  border-color: var(--chinese-primary);
}

.filter-tag.active {
  color: white;
  background: var(--chinese-primary);
  border-color: var(--chinese-primary);
  box-shadow: var(--chinese-shadow);
}

.filter-count {
  font-size: 14px;
  color: var(--text-muted);
}

.wenyanwen-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.wenyanwen-card {
  overflow: hidden;
  padding: 0;
}

.card-cover {
  height: 140px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.cover-icon {
  font-size: 48px;
  color: rgba(255, 255, 255, 0.95);
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.15));
}

.cover-dynasty {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 2px 10px;
  border-radius: var(--radius-full);
  font-size: 12px;
  font-weight: 600;
  background: rgba(255, 255, 255, 0.25);
  color: white;
  backdrop-filter: blur(4px);
}

.card-body {
  padding: 16px 20px 20px;
}

.card-body h3 {
  font-size: 18px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 6px;
}

.card-meta {
  font-size: 13px;
  color: var(--text-secondary);
  margin-bottom: 12px;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.difficulty-tag {
  padding: 2px 10px;
  border-radius: var(--radius-full);
  font-size: 12px;
  font-weight: 600;
}

.difficulty-tag.easy {
  background: var(--success-50);
  color: var(--success-dark);
}

.difficulty-tag.medium {
  background: var(--warning-50);
  color: var(--warning-dark);
}

.difficulty-tag.hard {
  background: var(--danger-50);
  color: var(--danger-dark);
}

.student-count {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: var(--text-muted);
}

@media (max-width: 1023px) {
  .wenyanwen-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 640px) {
  .wenyanwen-grid {
    grid-template-columns: 1fr;
  }

  .filter-bar {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
}
</style>
