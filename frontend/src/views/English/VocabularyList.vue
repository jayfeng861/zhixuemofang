<script setup lang="ts">
import { ref } from 'vue'
import PageHeader from '@/components/PageHeader.vue'
import EmptyState from '@/components/EmptyState.vue'

const activeFilter = ref('all')
const filters = [
  { label: '全部', value: 'all' },
  { label: '必考', value: 'required' },
  { label: '高频', value: 'frequent' },
  { label: '拓展', value: 'extension' },
]

const vocabulary = ref([
  { id: 1, word: 'environment', phonetic: '/ɪnˈvaɪrənmənt/', meaning: '环境', example: 'We should protect the environment.', category: 'required', learned: true },
  { id: 2, word: 'achieve', phonetic: '/əˈtʃiːv/', meaning: '达到；实现', example: 'You can achieve your dream.', category: 'frequent', learned: true },
  { id: 3, word: 'communicate', phonetic: '/kəˈmjuːnɪkeɪt/', meaning: '交流；沟通', example: 'It is important to communicate well.', category: 'required', learned: false },
  { id: 4, word: 'advantage', phonetic: '/ədˈvɑːntɪdʒ/', meaning: '优势；有利条件', example: 'What are the advantages?', category: 'frequent', learned: false },
  { id: 5, word: 'experience', phonetic: '/ɪkˈspɪəriəns/', meaning: '经验；经历', example: 'Learning from experience is important.', category: 'required', learned: false },
  { id: 6, word: 'determine', phonetic: '/dɪˈtɜːmɪn/', meaning: '决定；确定', example: 'Determine to work hard.', category: 'extension', learned: false },
  { id: 7, word: 'opportunity', phonetic: '/ˌɒpəˈtjuːnəti/', meaning: '机会', example: 'Don\'t miss this opportunity.', category: 'frequent', learned: true },
  { id: 8, word: 'volunteer', phonetic: '/ˌvɒlənˈtɪə/', meaning: '志愿者', example: 'She works as a volunteer.', category: 'extension', learned: false },
])

const flippedCards = ref<Set<number>>(new Set())

function toggleFlip(id: number) {
  if (flippedCards.value.has(id)) {
    flippedCards.value.delete(id)
  } else {
    flippedCards.value.add(id)
  }
}

const totalWords = vocabulary.value.length
const learnedWords = vocabulary.value.filter(v => v.learned).length
</script>

<template>
  <div class="page-container">
    <PageHeader title="词汇记忆" subtitle="卡片翻转，高效记忆">
      <template #actions>
        <el-button type="primary" class="action-btn" @click="$router.push('/english/vocabulary/test')">
          <el-icon><Checked /></el-icon>
          <span>词汇测试</span>
        </el-button>
      </template>
    </PageHeader>

    <div class="progress-section">
      <div class="progress-info">
        <div class="progress-left">
          <el-icon class="progress-icon"><Reading /></el-icon>
          <span class="progress-label">学习进度</span>
          <span class="progress-count">{{ learnedWords }}/{{ totalWords }}</span>
        </div>
        <span class="progress-percent">{{ Math.round(learnedWords / totalWords * 100) }}%</span>
      </div>
      <div class="progress-bar">
        <div class="progress-bar-fill" :style="{ width: (learnedWords / totalWords * 100) + '%' }"></div>
      </div>
    </div>

    <div class="filter-bar">
      <div class="filter-group">
        <span v-for="f in filters" :key="f.value" class="filter-tag" :class="{ active: activeFilter == f.value }" @click="activeFilter = f.value">{{ f.label }}</span>
      </div>
    </div>

    <div class="vocab-grid">
      <div
        v-for="item in vocabulary"
        :key="item.id"
        class="vocab-card"
        :class="{ flipped: flippedCards.has(item.id), learned: item.learned }"
        @click="toggleFlip(item.id)"
      >
        <div class="card-face front">
          <div class="card-status" v-if="item.learned">
            <el-icon><Check /></el-icon>
          </div>
          <h3>{{ item.word }}</h3>
          <p class="phonetic">{{ item.phonetic }}</p>
          <span class="flip-hint">
            <el-icon><Refresh /></el-icon>
            点击翻转
          </span>
        </div>
        <div class="card-face back">
          <p class="meaning">{{ item.meaning }}</p>
          <p class="example">{{ item.example }}</p>
          <span class="category-tag" :class="item.category">{{ { required: '必考', frequent: '高频', extension: '拓展' }[item.category as string] }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.action-btn {
  --el-button-bg-color: var(--english-primary) !important;
  --el-button-border-color: var(--english-primary) !important;
  --el-button-hover-bg-color: #0891B2 !important;
  --el-button-hover-border-color: #0891B2 !important;
  --el-button-active-bg-color: #0E7490 !important;
  --el-button-active-border-color: #0E7490 !important;
  box-shadow: var(--english-shadow) !important;
}

.progress-section {
  margin-bottom: 24px;
  padding: 20px 24px;
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border);
}

.progress-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 12px;
}

.progress-left {
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.progress-icon {
  font-size: 18px;
  color: var(--english-primary);
}

.progress-label {
  font-weight: 600;
  color: var(--text-primary);
}

.progress-count {
  color: var(--text-muted);
}

.progress-percent {
  font-weight: 700;
  color: var(--english-primary);
  font-size: 18px;
}

.progress-bar {
  height: 8px;
  background: var(--bg-muted);
  border-radius: var(--radius-full);
  overflow: hidden;
}

.progress-bar-fill {
  height: 100%;
  border-radius: var(--radius-full);
  background: var(--english-gradient);
  transition: width var(--transition-slow);
}

.filter-bar {
  display: flex;
  margin-bottom: 20px;
}

.filter-group {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
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
  color: var(--english-primary);
  border-color: var(--english-primary);
}

.filter-tag.active {
  color: white;
  background: var(--english-primary);
  border-color: var(--english-primary);
  box-shadow: var(--english-shadow);
}

.vocab-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.vocab-card {
  height: 200px;
  perspective: 800px;
  cursor: pointer;
  position: relative;
}

.card-face {
  position: absolute;
  inset: 0;
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-shadow: var(--shadow-md);
  transition: transform 0.6s;
  backface-visibility: hidden;
  border: 2px solid transparent;
}

.front {
  transform: rotateY(0);
}

.back {
  transform: rotateY(180deg);
  background: var(--english-gradient);
  color: white;
}

.vocab-card.flipped .front {
  transform: rotateY(-180deg);
}

.vocab-card.flipped .back {
  transform: rotateY(0);
}

.vocab-card.learned .card-face.front {
  border-color: var(--success);
}

.card-status {
  position: absolute;
  top: 12px;
  right: 12px;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: var(--success);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 700;
}

.front h3 {
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 4px;
}

.phonetic {
  font-size: 14px;
  color: var(--text-muted);
  margin-bottom: 12px;
  font-style: italic;
}

.flip-hint {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: var(--text-muted);
}

.back .meaning {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 12px;
  color: white;
}

.back .example {
  font-size: 13px;
  opacity: 0.9;
  text-align: center;
  margin-bottom: 12px;
  font-style: italic;
  color: white;
}

.category-tag {
  padding: 3px 12px;
  border-radius: var(--radius-full);
  font-size: 12px;
  font-weight: 600;
  background: rgba(255, 255, 255, 0.25);
  color: white;
}

.category-tag.required {
  background: rgba(255, 255, 255, 0.35);
}

@media (max-width: 1023px) {
  .vocab-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 767px) {
  .vocab-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 480px) {
  .vocab-grid {
    grid-template-columns: 1fr;
  }
}
</style>
